package com.dpstudio.dev.jdbc.annotation;


import java.lang.annotation.*;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/15.
 * @Time: 3:10 下午.
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Where {

    /**
     * conds集合
     * @return
     */
    Cond[] conds() default {};

    /**
     * 排序
     * @return
     */
    OrderBy[] orderBys() default {};
}
