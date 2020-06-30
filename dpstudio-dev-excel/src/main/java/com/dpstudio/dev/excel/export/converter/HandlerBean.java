package com.dpstudio.dev.excel.export.converter;

import com.dpstudio.dev.excel.export.annotation.DConverter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * @author 徐建鹏
 * @Date 2020/5/20.
 * @Time: 16:57.
 * @Description:
 */
public class HandlerBean {

    private Object dataHandle;

    private Method method;

    private Class<?> parameterType;

    private HandlerBean(DConverter dConverter) throws Exception {
        Class handleClass = dConverter.handleClass();
        if (handleClass != null && !handleClass.isInterface() && StringUtils.isNotBlank(dConverter.method())) {
            dataHandle = handleClass.newInstance();
            method = dataHandle.getClass().getMethod(dConverter.method(), String.class);
        }
    }

    public static HandlerBean create(DConverter dConverter) throws Exception {
        return new HandlerBean(dConverter);
    }

    public Object getDataHandle() {
        return dataHandle;
    }

    public void setDataHandle(Object dataHandle) {
        this.dataHandle = dataHandle;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }
}
