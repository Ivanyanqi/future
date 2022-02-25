package cn.ivan.futuredemo;


import cn.ivan.furure.spring.FutureSpringInitializer;
import cn.ivan.future.core.FutureDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanqi69
 * @date 2021/5/17
 */
@Configuration
public class Config {


    @Bean
    public FutureSpringInitializer futureSpringInitializer(){
       FutureSpringInitializer futureSpringInitializer = new FutureSpringInitializer();
       futureSpringInitializer.setConfigPath("application.properties");
       return futureSpringInitializer;
    }

    @Bean
    public FutureDispatcher futureDispatcher(FutureSpringInitializer initializer){
        return initializer.initialize();
    }

}
