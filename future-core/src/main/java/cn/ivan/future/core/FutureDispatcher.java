package cn.ivan.future.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author yanqi69
 * @date 2021/5/14
 */
@Slf4j
public class FutureDispatcher {

    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    private List<HandlerMapping> handlerMappings = new ArrayList<>();


    public void dispatch(FutureRequest request, FutureResponse response) {
        // 获取handlerExecuteChain
        HandlerExecuteChain hc = getHandler(request);
        if (hc == null) {
            return;
        }
        // 获取 Adapter
        HandlerAdapter ha = getAdapter(hc.getHandler());
        if (ha == null) {
            return;
        }
        // 执行前置拦截
        if (!hc.applyPreHandle(request, response)) {
            return;
        }
        // handler
        ha.handle(request, response, hc.getHandler());

        // 执行后置拦截
        hc.applyPostHandle(request, response);

    }


    private HandlerExecuteChain getHandler(FutureRequest request) {
        return handlerMappings.stream().map(handlerMapping -> handlerMapping.getHandler(request)).filter(Objects::nonNull).findFirst().orElse(null);
    }


    private HandlerAdapter getAdapter(Object handler) {
        return handlerAdapters.stream().filter(adapter -> adapter.supports(handler)).findFirst().orElse(null);
    }

    @Slf4j
    public static class Builder {

        /**
         * 配置文件路径
         */
        private String configPath;

        private static Map<String, ConfigProperties> configPropertiesMap = new HashMap<>();

        private List<FutureInterceptor> interceptorList;

        private List<HandlerAdapter> handlerAdapters;

        private List<HandlerMapping> handlerMappings;

        private List<AbstractHttpHandler> httpHandlerList;

        public Builder() {

        }

        public Builder(String configPath) {
            try {
                Properties properties = PropertiesLoaderUtils.loadAllProperties(configPath);

                PropertiesResolver resolver = new PropertiesResolver(properties);
                properties.forEach((k, v) -> {
                    //获取functionId
                    String[] split = ((String) k).split("\\.");
                    if (split.length <= 1) {
                        return;
                    }
                    String functionId = split[0];
                    ConfigProperties configProperties = configPropertiesMap.computeIfAbsent(functionId, f->{
                        ConfigProperties c = new ConfigProperties();
                        c.setFunctionId(f);
                        return c;
                    });
                    resolver.resolver(configProperties, split[1], (String) v);
                });
                log.info("properties:{}", configPropertiesMap);
            } catch (IOException e) {
                log.error("load properties error", e);
            }
        }


        public Builder addHandlerAdapters(HandlerAdapter... handlerAdapters) {
            if (this.handlerAdapters == null) {
                this.handlerAdapters = new ArrayList<>();
            }
            this.handlerAdapters.addAll(Arrays.asList(handlerAdapters));
            return this;
        }

        public Builder addHandlerAdapters(List<HandlerAdapter> handlerAdapters) {
            if (this.handlerAdapters == null) {
                this.handlerAdapters = new ArrayList<>();
            }
            this.handlerAdapters.addAll(handlerAdapters);
            return this;
        }

        public Builder addHandlerMappings(HandlerMapping... handlerMappings) {
            if (this.handlerMappings == null) {
                this.handlerMappings = new ArrayList<>();
            }
            this.handlerMappings.addAll(Arrays.asList(handlerMappings));
            return this;
        }

        public Builder addHandlerMappings(List<HandlerMapping> handlerMappings) {
            if (this.handlerMappings == null) {
                this.handlerMappings = new ArrayList<>();
            }
            this.handlerMappings.addAll(handlerMappings);
            return this;
        }

        public Builder addInterceptors(FutureInterceptor... interceptors) {
            if (this.interceptorList == null) {
                interceptorList = new ArrayList<>();
            }
            this.interceptorList.addAll(Arrays.asList(interceptors));
            return this;
        }

        public Builder addInterceptors(List<FutureInterceptor> interceptors) {
            if (this.interceptorList == null) {
                interceptorList = new ArrayList<>();
            }
            this.interceptorList.addAll(interceptors);
            return this;
        }

        public Builder addHttpHandlers(AbstractHttpHandler... httpHandlers) {
            if (this.httpHandlerList == null) {
                this.httpHandlerList = new ArrayList<>();
            }
            this.httpHandlerList.addAll(Arrays.asList(httpHandlers));
            return this;
        }

        public Builder addHttpHandlers(List<AbstractHttpHandler> httpHandlers) {
            if (this.httpHandlerList == null) {
                this.httpHandlerList = new ArrayList<>();
            }
            this.httpHandlerList.addAll(httpHandlers);
            return this;
        }


        public FutureDispatcher build() {
            FutureDispatcher futureDispatcher = new FutureDispatcher();
            // 初始化默认 的 handlerMapping，handlerAdapter, HttpHandler
            //get请求
            List<AbstractHttpHandler> defaultHttpHandlers = new ArrayList<>();
            defaultHttpHandlers.add(new GetHttpHandler());
            defaultHttpHandlers.add(new PostHttpHandler());
            defaultHttpHandlers.add(new PostFileHttpHandler());
            if (this.httpHandlerList != null && this.httpHandlerList.size() > 0) {
                defaultHttpHandlers.addAll(this.httpHandlerList);
            }
            RequestFunctionHandlerMapping handlerMapping = new RequestFunctionHandlerMapping(defaultHttpHandlers);

            handlerMapping.setInterceptorList(this.interceptorList);
            configPropertiesMap.forEach((functionId, config) -> {
                handlerMapping.initHandlerExecuteChain(functionId, config.getFunction());
            });

            futureDispatcher.handlerMappings.add(handlerMapping);
            if (this.handlerMappings != null && this.handlerMappings.size() > 0) {
                futureDispatcher.handlerMappings.addAll(this.handlerMappings);
            }
            HttpHandlerAdapter httpHandlerAdapter = new HttpHandlerAdapter();
            futureDispatcher.handlerAdapters.add(httpHandlerAdapter);
            if (this.handlerAdapters != null && this.handlerAdapters.size() > 0) {
                futureDispatcher.handlerAdapters.addAll(this.handlerAdapters);
            }
            return futureDispatcher;
        }


        public static ConfigProperties getConfig(String functionId) {
            return configPropertiesMap.get(functionId);
        }

    }


}
