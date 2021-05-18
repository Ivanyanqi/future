package cn.ivan.future.core.handler;

import cn.ivan.future.core.HttpFutureRequest;
import cn.ivan.future.core.HttpFutureResponse;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yanqi69
 * @date 2021/5/18
 */
public class GetFileHttpHandler extends AbstractHttpHandler{
    @Override
    public boolean supports(HttpFutureRequest request) {
        return "file".equals(request.getResponseType());
    }

    @Override
    public Request buildRequest(HttpFutureRequest request) {
        return new Request.Builder().url(request.getFunction()).build();
    }

    @Override
    public void setResponse(Response execute, HttpFutureResponse futureResponse) throws IOException {
        ResponseBody body = execute.body();
        HttpServletResponse response = futureResponse.getResponse();
        if(body != null){
            response.setContentType(body.contentType().toString());
            response.setContentLength((int) body.contentLength());
            String header = execute.header("Content-Disposition");
            if(header == null){
                String url = execute.request().url().toString();
                String filename = url.substring(url.lastIndexOf("/"));
                header = "attachment;filename=" + filename;
            }
            response.setHeader("Content-Disposition",header);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(body.bytes());
            outputStream.flush();
        }
    }
}
