package cn.ivan.future.core;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanqi69
 * @date 2021/5/14
 */
public class RequestFunctionHandlerMapping implements HandlerMapping{


    private Map<String,HandlerExecuteChain> handlerMap;

    private List<AbstractHttpHandler> httpHandlerList;

    private List<FutureInterceptor> interceptorList;


    public RequestFunctionHandlerMapping(List<AbstractHttpHandler> httpHandlerList) {
        this.httpHandlerList = httpHandlerList;
    }


    @Override
    public HandlerExecuteChain getHandler(FutureRequest request) {
        String functionId = request.getFunctionId();
        if(!StringUtils.hasText(functionId)){
            return null;
        }
        HandlerExecuteChain handler = this.handlerMap.get(functionId);

        if(handler == null){
            //lookupForHandler
            return this.lookupForHandler((HttpFutureRequest) request);
        }
        return handler;
    }

    private HandlerExecuteChain lookupForHandler(HttpFutureRequest request){
        AbstractHttpHandler abstractHttpHandler = httpHandlerList.stream().filter(h -> h.supports(request)).findFirst().orElse(null);
        return initHandlerExecuteChain(request.getFunctionId(), abstractHttpHandler);
    }


    public HandlerExecuteChain initHandlerExecuteChain(String functionId,Object handler){
        HandlerExecuteChain handlerExecuteChain = new HandlerExecuteChain(handler);
        ConfigProperties config = FutureDispatcher.Builder.getConfig(functionId);
        for (FutureInterceptor futureInterceptor : interceptorList) {
            if(config.getInterceptors().contains(futureInterceptor.getClass().getName())){
                handlerExecuteChain.addInterceptor(futureInterceptor);
                continue;
            }
            if(futureInterceptor.getClass().isAnnotationPresent(Interceptor.class)){
                Interceptor annotation = futureInterceptor.getClass().getAnnotation(Interceptor.class);
                String[] values = annotation.values();
                if(Arrays.stream(values).collect(Collectors.toSet()).contains(functionId)){
                    handlerExecuteChain.addInterceptor(futureInterceptor);
                }
            }
        }
        return handlerExecuteChain;
    }

    public void setInterceptorList(List<FutureInterceptor> interceptorList) {
        this.interceptorList = interceptorList;
    }
}
