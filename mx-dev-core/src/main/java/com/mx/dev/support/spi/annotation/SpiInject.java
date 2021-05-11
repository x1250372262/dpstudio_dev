package com.mx.dev.support.spi.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpiInject {

    String className() default "";
}