package cn.ivan.futuredemo;

import cn.ivan.future.core.FutureInterceptor;
import cn.ivan.future.core.FutureRequest;
import cn.ivan.future.core.FutureResponse;
import cn.ivan.future.core.Interceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yanqi69
 * @date 2021/5/17
 */
@Slf4j
@Component
@Interceptor("xxxx")
public class TestInterceptor implements FutureInterceptor {
    @Override
    public boolean preHandle(FutureRequest request, FutureResponse response, Object handler) {

        log.info("请求 百度 前 ======= {}",handler.getClass().getName());
        return true;
    }

    @Override
    public void postHandle(FutureRequest request, FutureResponse response, Object handler) {
        log.info("请求 百度 后 ======= {}",handler.getClass().getName());
    }
}
