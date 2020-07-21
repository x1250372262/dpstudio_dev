package com.dpstudio.dev.security.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Group {

    /**
     * 权限集合
     * @return
     */
    Permission[] permissions() default {};

}
