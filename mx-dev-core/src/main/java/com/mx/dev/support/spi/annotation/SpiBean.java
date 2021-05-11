package com.dpstudio.dev.support.spi.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpiBean {

    String className() default "";
}