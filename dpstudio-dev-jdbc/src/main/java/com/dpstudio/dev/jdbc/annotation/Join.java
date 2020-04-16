package com.dpstudio.dev.jdbc.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Join {

    /**
     * 连接方式
     *
     * @return
     */
    JoinWay joinWay() default JoinWay.LEFT;

    /**
     * 表名
     *
     * @return
     */
    String tableName() default "";

    /**
     * 字段
     * @return
     */
    String field() default "";

    /**
     * 别名
     *
     * @return
     */
    String alias() default "";

    /**
     * 连接字段
     * @return
     */
    String JoinField() default "";

    /**
     * 连接别名
     *
     * @return
     */
    String JoinAlias() default "";




    enum JoinWay {

        LEFT, RIGHT, INNER

    }


}