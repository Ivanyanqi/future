package cn.ivan.future.core;

import lombok.Data;

/**
 * @author yanqi69
 * @date 2021/5/14
 */

@Data
public class FutureRequest {

    private String functionId;

    public FutureRequest(String functionId){
        this.functionId = functionId;
    }
}
