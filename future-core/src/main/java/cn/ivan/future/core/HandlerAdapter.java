package cn.ivan.future.core;

/**
 * @author yanqi69
 * @date 2021/5/14
 */
public interface HandlerAdapter {


    boolean supports(Object handler);


    void handle(FutureRequest request,FutureResponse response,Object handler);


}
