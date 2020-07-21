package com.dpstudio.dev.security.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

    /**
     * ¬
     * 权限组Id
     *
     * @return
     */
    String groupId() default "default";

    String groupName() default "默认";

    /**
     * 权限名称
     *
     * @return
     */
    String name();

    /**
     * 权限码
     *
     * @return
     */
    String code();


}
