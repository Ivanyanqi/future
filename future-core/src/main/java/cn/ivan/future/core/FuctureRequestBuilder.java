package cn.ivan.future.core;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yanqi69
 * @date 2021/5/17
 */
public class FuctureRequestBuilder {


    public static FutureRequest build(String functionId, HttpServletRequest ... requests){
        ConfigProperties config = FutureDispatcher.Builder.getConfig(functionId);
        if(config == null){
            throw new RuntimeException("not found request");
        }
        if(!StringUtils.hasText(config.getType())){
            return new FutureRequest(functionId);
        }
        if("HTTP".equals(config.getType())){
            if(requests == null || requests.length ==0){
                throw new RuntimeException("http proxy must have httpServletRequest");
            }
            HttpFutureRequest request = new HttpFutureRequest(functionId, requests[0]);
            request.setMethod(config.getMethod());
            request.setUrl(config.getFunction());
            return request;
        }
        return new FutureRequest(functionId);
    }


}
