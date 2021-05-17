package cn.ivan.future.core;

import java.lang.annotation.*;

/**
 * @author yanqi69
 * @date 2021/5/17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Interceptor{

    String[] value() default {};
}
