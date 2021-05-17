package cn.ivan.future.core;

/**
 * @author yanqi69
 * @date 2021/5/14
 */
public class HttpHandlerAdapter implements HandlerAdapter{


    @Override
    public boolean supports(Object handler) {
        return handler instanceof AbstractHttpHandler;
    }

    @Override
    public void handle(FutureRequest request, FutureResponse response, Object handler) {
        ((AbstractHttpHandler)handler).doService(request, response);
    }
}
