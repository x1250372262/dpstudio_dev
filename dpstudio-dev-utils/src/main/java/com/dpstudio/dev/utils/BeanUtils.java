package com.dpstudio.dev.utils;

import net.ymate.platform.commons.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/6/5.
 * @Time: 8:10 上午.
 * @Description:
 */
public class BeanUtils {

    public interface BeanUtilCallBack<S, T> {
        /**
         * 定义默认回调方法
         *
         * @param s
         * @param t
         */
        void callBack(S s, T t);
    }

    /**
     * 复制bean
     *
     * @param source 要复制的
     * @param target 目标对象
     * @return 目标对象
     */
    public static <S, T> T copy(S source, Supplier<T> target) {
        return copy(source, target, null);
    }

    /**
     * 复制bean
     *
     * @param source   要复制的
     * @param target   目标对象
     * @param callBack 处理额外数据接口
     * @return 目标对象
     */
    public static <S, T> T copy(S source, Supplier<T> target, BeanUtilCallBack<S, T> callBack) {
        T t = ClassUtils.wrapper(source).duplicate(target.get());
        if (callBack != null) {
            // 回调
            callBack.callBack(source, t);
        }
        return t;
    }

    /**
     * 复制集合
     *
     * @param sources 要复制的
     * @param target  目标对象
     * @return 目标对象
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target) {
        return copyList(sources, target, null);
    }


    /**
     * 复制集合
     *
     * @param sources  要复制的
     * @param target   目标对象
     * @param callBack 处理额外数据接口
     * @return 目标对象
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target, BeanUtilCallBack<S, T> callBack) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            ClassUtils.wrapper(source).duplicate(t);
            list.add(t);
            if (callBack != null) {
                // 回调
                callBack.callBack(source, t);
            }
        }
        return list;
    }
}