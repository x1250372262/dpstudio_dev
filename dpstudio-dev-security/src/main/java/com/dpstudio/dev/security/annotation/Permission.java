package com.dpstudio.dev.security.annotation;

import java.lang.annotation.*;


/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 权限注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

    /**
     * 权限组名称
     *
     * @return
     */
    String groupName() default "";

    /**
     * ¬
     * 权限组Id
     *
     * @return
     */
    String groupId() default "";

    /**
     * 权限名称
     *
     * @return
     */
    String name() default "";

    /**
     * 权限码
     *
     * @return
     */
    String code() default "";


}
