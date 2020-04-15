package com.dpstudio.dev.excel.analysis.bean;

import com.dpstudio.dev.excel.analysis.annotation.ImportColumn;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-07-20.
 * @Time: 14:18.
 * @Description: 数据处理bean
 */
public class HandlerBean {

    private Object dataHandle;

    private Method method;

    private Class<?> parameterType;

    private HandlerBean(ImportColumn importColumn) throws Exception {
        Class handleClass = importColumn.handleClass();
        if (handleClass != null && !handleClass.isInterface() && StringUtils.isNotBlank(importColumn.method())) {
            dataHandle = handleClass.newInstance();
            //得到方法对象,有参的方法需要指定参数类型
            Class<?> parameterTypes = importColumn.parameterType();
            if (parameterTypes != null) {
                method = dataHandle.getClass().getMethod(importColumn.method(), parameterTypes);
            } else {
                method = dataHandle.getClass().getMethod(importColumn.method());
            }
        }
    }

    public static HandlerBean create(ImportColumn importColumn) throws Exception {
        return new HandlerBean(importColumn);
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
