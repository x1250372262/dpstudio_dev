package com.dpstudio.dev.support.jdbc.annotation;

import net.ymate.platform.persistence.jdbc.query.Cond;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CondConf {

    Cond.OPT opt();

    String prefix() default "";

    String field();

    boolean exprNotEmpty() default true;


}
