package com.dpstudio.dev.doc.core.resolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 忽略生成接口文档,标注了该注解的接口,将不会生成接口文档
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreApi {
}
