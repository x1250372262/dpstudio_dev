package com.dpstudio.dev.excel.analysis.annotation;
import com.dpstudio.dev.excel.analysis.IValidate;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validate {

    /**
     * 是否必填
     *
     * @return
     */
    boolean required() default false;

    /**
     * 自定义验证类
     *
     * @return
     */
    Class<? extends IValidate> validateClass() default IValidate.class;

    /**
     * 方法名称
     *
     * @return
     */
    String method() default "";

    /**
     * 方法参数类型
     *
     * @return
     */
    Class<?> parameterType() default Object.class;
}
