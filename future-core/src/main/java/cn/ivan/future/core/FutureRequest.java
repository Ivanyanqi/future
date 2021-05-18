package cn.ivan.future.core;

import lombok.Builder;
import lombok.Data;

/**
 * @author yanqi69
 * @date 2021/5/14
 */
@Data
public class FutureRequest {

    private String functionId;

    private String function;

    private String method;

    private String requestBody;

    private String responseType;

    /**
     * 二进制文件
     */
    private byte[] bytes;

    public FutureRequest(String functionId){
        this.functionId = functionId;
        ConfigProperties config = FutureDispatcher.Builder.getConfig(functionId);
        if(config == null){
            throw new RuntimeException("not found request");
        }
        this.function = config.getFunction();
        this.method = config.getMethod();
        this.responseType = config.getResponseType();
    }
}
