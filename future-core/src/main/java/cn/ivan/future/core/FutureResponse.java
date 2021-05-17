package cn.ivan.future.core;

import lombok.Data;

/**
 * @author yanqi69
 * @date 2021/5/14
 */
@Data
public class FutureResponse {

    private String responseString;

    private byte[] bytes;

    private String charset;

}
