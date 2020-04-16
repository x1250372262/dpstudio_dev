package com.dpstudio.dev.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Field {

    /**
     * 别名
     *
     * @return
     */
    String alias() default "";

    /**
     * 数据库名称
     *
     * @return
     */
    String dbField() default "";

    /**
     * 表别名
     *
     * @return
     */
    String tableAlias() default "";


    /**
     * 完整字段名 写了这个其他参数无效
     *
     * @return
     */
    String filed() default "";


}