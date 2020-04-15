package com.dpstudio.dev.excel.analysis.annotation;
import com.dpstudio.dev.excel.analysis.IDataHandler;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ImportColumn {

    /**
     * @return 列下标
     */
    int idx() default 0;

    /**
     * 表头
     *
     * @return
     */
    String title() default "";

    /**
     * 数据处理类
     *
     * @return
     */
    Class<? extends IDataHandler> handleClass() default IDataHandler.class;

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
