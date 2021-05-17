package cn.ivan.future.core.handler;

import cn.ivan.future.core.HttpFutureRequest;
import cn.ivan.future.core.handler.AbstractHttpHandler;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author yanqi69
 * @date 2021/5/17
 */
public class PostFileHttpHandler extends AbstractHttpHandler {

    @Override
    public boolean supports(HttpFutureRequest request) {
        return "POST".equals(request.getMethod()) && request.getRequest().getContentType().contains("multipart/form-data");
    }

    @Override
    public Request buildRequest(HttpFutureRequest request) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), request.getBytes());
        return new Request.Builder().url(request.getFuntion()).post(requestBody).build();
    }
}
