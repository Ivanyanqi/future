package cn.ivan.furure.spring;

import cn.ivan.future.core.*;
import cn.ivan.future.core.adapter.HandlerAdapter;
import cn.ivan.future.core.handler.AbstractHttpHandler;
import cn.ivan.future.core.mapping.HandlerMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @date 2021/5/17
 */
@Slf4j
public class FutureSpringInitializer implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private String configPath;


    public FutureDispatcher initialize(){
        log.info("future dispatcher start init ");
        return new FutureDispatcher.Builder(configPath)
                // handlerMapping
                .addHandlerMappings(typeList(HandlerMapping.class))
                // interceptors
                .addInterceptors(typeList(FutureInterceptor.class))
                // handlerAdapters
                .addHandlerAdapters(typeList(HandlerAdapter.class))
                //http handler
                .addHttpHandlers(typeList(AbstractHttpHandler.class))
                .build();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    private <T> List<T> typeList(Class<T> clazz) {
        Map<String, T> beansOfType = applicationContext.getBeansOfType(clazz);
        List<T> beans = new ArrayList<>(beansOfType.values());
        AnnotationAwareOrderComparator.sort(beans);
        return beans;
    }
}
