package com.dpstudio.dev.security.annotation;

import java.lang.annotation.*;

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
