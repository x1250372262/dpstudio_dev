package com.dpstudio.dev.vo;

import net.ymate.platform.core.util.ClassUtils;

import java.io.Serializable;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/5/19.
 * @Time: 8:46 上午.
 * @Description:
 */
public abstract class BaseVo<T> implements Serializable {

    protected abstract T me();

    public T copy(Object target) throws Exception {
        return ClassUtils.wrapper(target).duplicate(me());
    }

}
