package cn.ivan.future.core;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author ivanqi
 * @date 2021/5/15
 */
@Slf4j
public class PropertiesResolver {


    private Properties properties;

    Map<String , BiConsumer<ConfigProperties,String>> map = new HashMap<>();

    public PropertiesResolver(Properties properties){
        this.properties = properties;
        map.put("type",this::setType);
        map.put("handler",this::setHandler);
        map.put("interceptors",this::setInterceptors);


    }


    public void resolver(ConfigProperties configProperties, String key, String value){
        BiConsumer<ConfigProperties, String> consumer = map.get(key);
        if(consumer == null){
            log.error("can't resolver properties {} ,please check your properties", key);
            return;
        }
        consumer.accept(configProperties,value);
    }


    private void setType(ConfigProperties configProperties ,String value){
        configProperties.setType(value);
    }

    private void setHandler(ConfigProperties configProperties ,String value){
        configProperties.setHandler(value);
    }

    private void setInterceptors(ConfigProperties configProperties,String value){
        if(value == null){
            return;
        }
        configProperties.setInterceptors(value.split(","));
    }









}
