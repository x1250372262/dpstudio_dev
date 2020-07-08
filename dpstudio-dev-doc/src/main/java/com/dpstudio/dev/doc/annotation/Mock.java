package com.dpstudio.dev.doc.annotation;

import java.lang.annotation.*;

/**
 * @author mengxiang
 * @Date 2020.07.08.
 * @Time: 14:30.
 * @Description: doc注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mock {

    /**
     * 返回的json
     * @return 返回的json
     */
    String value() default "";

    /**
     * 是否可用
     * @return 是否可用
     */
    boolean enabled() default true;
}
