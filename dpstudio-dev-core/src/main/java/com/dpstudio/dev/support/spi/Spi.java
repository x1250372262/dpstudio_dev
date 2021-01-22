package com.dpstudio.dev.support.spi;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Spi {

    String className() default "";
}