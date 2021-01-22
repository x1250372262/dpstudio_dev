package com.dpstudio.dev.support.log.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogGroup {

    Log[] logs() default {};

    /**
     * 如果使用spi，指定具体的类
     *
     * @return
     */
    String className() default "";
}
