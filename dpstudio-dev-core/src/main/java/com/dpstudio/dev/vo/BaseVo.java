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

    /**
     * 从 目标类复制数据
     *
     * @param target
     * @return
     * @throws Exception
     */
    public void copy(Object target) throws Exception {
        ClassUtils.wrapper(target).duplicate(me());
    }

    /**
     * 把数据粘贴给目标类
     *
     * @param target
     * @return
     * @throws Exception
     */
    public void paste(Object target) throws Exception {
        ClassUtils.wrapper(me()).duplicate(target);
    }

}
