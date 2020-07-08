package com.dpstudio.dev.support.log.annotation;

import java.lang.annotation.*;

/**
 * @author mengxiang
 * @Date 2020.07.08.
 * @Time: 14:30.
 * @Description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogGroup {

    Log[] logs() default {};

    /**
     * 如果使用spi，指定具体的类
     *
     * @return
     */
    String className() default "";
}
