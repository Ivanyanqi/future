package cn.ivan.future.core;

import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yanqi69
 * @date 2021/5/14
 */
@Getter
public class HttpFutureResponse extends FutureResponse{

    private HttpServletResponse response;

    public HttpFutureResponse(HttpServletResponse response){
        this.response = response;
    }


}
