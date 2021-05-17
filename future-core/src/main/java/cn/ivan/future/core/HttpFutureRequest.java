package cn.ivan.future.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yanqi69
 * @date 2021/5/14
 */
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class HttpFutureRequest extends FutureRequest{

    private HttpServletRequest request;

    private String url;

    private String method;

    private String requestBody;

    /**
     * 二进制文件
     */
    private byte[] bytes;


    public HttpFutureRequest(String functionId,HttpServletRequest request){
        super(functionId);
        this.request = request;
    }




}
