package com.dpstudio.dev.excel.analysis.annotation;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Excle {

    /**
     * exlce取值方式
     */
    enum TYPE {
        TITLE, IDX
    }

    /**
     * exlce取值方式 默认索引
     *
     * @return
     */
    TYPE type() default TYPE.IDX;
}
