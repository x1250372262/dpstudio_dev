package com.dpstudio.dev.jdbc.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {

    /**
     * 表名
     *
     * @return
     */
    String tableName() default "";

    /**
     * 别名
     *
     * @return
     */
    String alias() default "";


    /**
     * join集合
     * @return
     */
    Join[] joins() default {};

}