package com.dpstudio.dev.excel.export.annotation;

import com.dpstudio.dev.excel.export.converter.IDpstudioDataHandler;

import java.lang.annotation.*;

/**
 * @author 徐建鹏
 * @Date 2020/5/20.
 * @Time: 16:57.
 * @Description: excel导出数据处理
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DConverter {

    /**
     * 数据处理类
     *
     * @return
     */
    Class<? extends IDpstudioDataHandler> handleClass() default IDpstudioDataHandler.class;

    /**
     * 方法名称
     *
     * @return
     */
    String method() default "";

}
