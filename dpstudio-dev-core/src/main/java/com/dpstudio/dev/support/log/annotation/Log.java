package com.dpstudio.dev.support.log.annotation;

import java.lang.annotation.*;

/**
 * @author mengxiang
 * @Date 2020.07.08.
 * @Time: 14:30.
 * @Description: 日志注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块类型
     *
     * @return
     */
    int moduleType() default 0;

    /**
     * 模块名称
     *
     * @return
     */
    String moduleName() default "默认";

    /**
     * 日志类型
     *
     * @return
     */
    int type() default 0;

    /**
     * 日志类型
     *
     * @return
     */
    String action() default "";


    /**
     * 用于区分多个日志
     *
     * @return
     */
    String logKey() default "default";
}
