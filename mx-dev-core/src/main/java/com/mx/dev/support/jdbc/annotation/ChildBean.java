package com.mx.dev.support.jdbc.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChildBean {

    /**
     * 子类类型
     *
     * @return
     */
    Class<?> childClass();

    /**
     * 分组名称
     *
     * @return
     */
    String groupName();

    /**
     * 列表名称
     *
     * @return
     */
    String childListName();
}
