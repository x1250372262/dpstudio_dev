package com.dpstudio.dev.jdbc.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface OrderBy {

    String tableAlias() default "";

    String dbFiled() default "";

    TYPE type() default TYPE.ASC;

    enum TYPE {
        ASC,DESC
    }
}