package com.dpstudio.dev.doc.annotation;

import java.lang.annotation.*;

/**
 * @author mengxiang
 * @Date 2020.07.08.
 * @Time: 14:30.
 * @Description: doc注解
 */
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Doc {

    String name() default "";
    String sdkName() default "";
}
