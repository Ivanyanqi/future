package cn.ivan.future.core;

import lombok.Data;

/**
 * @author ivanqi
 * @date 2021/5/15
 */
@Data
public class ConfigProperties {

    private String functionId;

    private String type;

    private String handler;

    private String[] interceptors;

}
