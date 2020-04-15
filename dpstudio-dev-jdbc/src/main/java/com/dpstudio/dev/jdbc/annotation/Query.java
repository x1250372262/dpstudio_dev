package com.dpstudio.dev.jdbc.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {

    String prefix() default "";

    String from() default "";

    String alias() default "";

}