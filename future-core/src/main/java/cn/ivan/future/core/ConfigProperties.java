package cn.ivan.future.core;

import lombok.Data;

import java.util.Set;

/**
 * @author ivanqi
 * @date 2021/5/15
 */
@Data
public class ConfigProperties {

    private String functionId;

    private String type;

    private String function;

    private String method;

    private Set<String> interceptors;

    private String responseType;

}
