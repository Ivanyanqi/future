package cn.ivan.future.core.handler;

import cn.ivan.future.core.HttpFutureRequest;
import cn.ivan.future.core.handler.AbstractHttpHandler;
import okhttp3.Request;

/**
 * @author yanqi69
 * @date 2021/5/17
 */
public class GetHttpHandler extends AbstractHttpHandler {


    @Override
    public boolean supports(HttpFutureRequest request) {
        return "GET".equals(request.getMethod());
    }

    @Override
    public Request buildRequest(HttpFutureRequest request) {
        return new Request.Builder().url(request.getFunction()).build();
    }


}
