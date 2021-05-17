package cn.ivan.future.core.adapter;

import cn.ivan.future.core.FutureRequest;
import cn.ivan.future.core.FutureResponse;

/**
 * @author yanqi69
 * @date 2021/5/14
 */
public interface HandlerAdapter {


    boolean supports(Object handler);


    void handle(FutureRequest request, FutureResponse response, Object handler);


}
