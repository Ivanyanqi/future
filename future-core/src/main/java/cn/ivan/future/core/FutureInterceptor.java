package cn.ivan.future.core;

/**
 *  拦截器
 * @author yanqi69
 * @date 2021/5/14
 */
public interface FutureInterceptor {


    public boolean preHandle(FutureRequest request,FutureResponse response,Object handler);



    public void postHandle(FutureRequest request,FutureResponse response,Object handler);


}
