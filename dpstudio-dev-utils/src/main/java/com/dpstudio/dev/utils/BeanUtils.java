package com.dpstudio.dev.utils;

import net.ymate.platform.core.util.ClassUtils;

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

    @FunctionalInterface
    public interface BeanUtilCallBack<S, T> {

        /**
         * 定义默认回调方法
         *
         * @param s
         * @param t
         */
        void callBack(S s, T t);
    }


    public static <S, T> T copy(S source, Supplier<T> target) {
        return copy(source, target, null);
    }

    public static <S, T> T copy(S source, Supplier<T> target, BeanUtilCallBack<S, T> callBack) {
        T t = ClassUtils.wrapper(source).duplicate(target.get());
        if (callBack != null) {
            // 回调
            callBack.callBack(source, t);
        }
        return t;
    }

    /**
     * 集合数据的拷贝
     *
     * @param sources: 数据源类
     * @param target:  目标类::new(eg: UserVO::new)
     * @return
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target) {
        return copyList(sources, target, null);
    }


    /**
     * 带回调函数的集合数据的拷贝（可自定义字段拷贝规则）
     *
     * @param sources:  数据源类
     * @param target:   目标类::new(eg: UserVO::new)
     * @param callBack: 回调函数
     * @return
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
