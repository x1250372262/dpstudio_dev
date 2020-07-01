package com.dpstudio.dev.annotation;

import java.lang.annotation.*;

@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Doc {

    String name() default "";
}
