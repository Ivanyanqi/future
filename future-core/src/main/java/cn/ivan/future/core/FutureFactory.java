package cn.ivan.future.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author yanqi69
 * @date 2021/5/14
 */
@Slf4j
public class FutureFactory {

    /**
     *  配置文件路径
     */
    private String configPath;

    public FutureFactory(String configPath){
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(configPath);

            Map<String,ConfigProperties> map = new HashMap<>();
            PropertiesResolver resolver = new PropertiesResolver(properties);
            properties.forEach((k,v)->{
                //获取functionId
                String[] split = ((String) k).split("\\.");
                if(split.length <= 1){
                    return;
                }
                String functionId = split[0];
                ConfigProperties configProperties = map.putIfAbsent(functionId, new ConfigProperties());
                resolver.resolver(configProperties,split[1],(String)v);
            });
            log.info("properties:{}",map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
