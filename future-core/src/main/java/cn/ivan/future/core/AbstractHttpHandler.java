package cn.ivan.future.core;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yanqi69
 * @date 2021/5/17
 */
@Slf4j
public abstract class AbstractHttpHandler {


    public abstract boolean supports(HttpFutureRequest request);



    public void doService(FutureRequest request,FutureResponse response){
        if(!(request instanceof HttpFutureRequest)){
            return;

        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(buildRequest((HttpFutureRequest) request));
        try {
            Response execute = call.execute();
            if(response == null){
                throw new RuntimeException("http request error");
            }
            if(!execute.isSuccessful()){
               throw new RuntimeException("http request error http status:{}" + execute.code() + "message :{}"+execute.message());
            }
            setResponse(execute,(HttpFutureResponse) response);
        } catch (IOException e) {
           log.error("http request error",e);
           throw new RuntimeException(e);
        }
    }


    public abstract Request buildRequest(HttpFutureRequest request);


    public void setResponse(Response execute,HttpFutureResponse response) throws IOException {
        ResponseBody body = execute.body();
        if(body == null){
            return;
        }
        String type = body.contentType().type();
        log.info("response ContentType {}",type);
        // 文件下载
        if("application/octet-stream".equals(type)){
            HttpServletResponse servletResponse = response.getResponse();
            servletResponse.setHeader("Content-Disposition",execute.header("Content-Disposition"));
            servletResponse.setContentType(type);
            servletResponse.setContentLength(Integer.parseInt(execute.header("Content-Length")));
            servletResponse.getOutputStream().write(body.bytes());
            return;
        }
        response.setResponseString(body.string());
    }




}
