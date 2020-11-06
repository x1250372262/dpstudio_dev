package com.dpstudio.dev.utils;


import net.ymate.platform.commons.util.ClassUtils;
import net.ymate.platform.core.persistence.IResultSet;
import net.ymate.platform.core.persistence.impl.DefaultResultSet;

import java.util.List;
import java.util.function.Supplier;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/10/15.
 * @Time: 8:47 上午.
 * @Description:
 */
public class ResultSetUtils {

    /**
     * 复制bean
     *
     * @param sources  要复制的
     * @param target   目标对象
     * @param callBack 处理额外数据接口
     * @return 目标对象
     */
    public static <S, T> IResultSet<T> copy(IResultSet<S> sources, Supplier<T> target, ClassUtils.IFieldValueFilter fieldValueFilter, BeanUtils.BeanUtilCallBack<S, T> callBack) {
        List<T> result = BeanUtils.copyList(sources.getResultData(), target, fieldValueFilter, callBack);
        return new DefaultResultSet<T>(result, sources.getPageNumber(), sources.getPageSize(), sources.getRecordCount());
    }

    /**
     * 复制bean
     *
     * @param sources  要复制的
     * @param target   目标对象
     * @return 目标对象
     */
    public static <S, T> IResultSet<T> copy(IResultSet<S> sources, Supplier<T> target) {
        List<T> result = BeanUtils.copyList(sources.getResultData(), target, null, null);
        return new DefaultResultSet<T>(result, sources.getPageNumber(), sources.getPageSize(), sources.getRecordCount());
    }
}
