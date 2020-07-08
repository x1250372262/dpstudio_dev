package com.dpstudio.dev.doc.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 标签指向的类具体信息
 */
public class ObjectInfo {

    /**
     * 源码在哪个类
     */
    private Class<?> type;

    /**
     * 上面的注释
     */
    private String comment;

    /**
     * 返回key
     */
    private String dataKey;

    /**
     * 返回数据类型
     */
    private String dataType;

    /**
     * 对象的属性
     */
    private List<FieldInfo> fieldInfos = new LinkedList<>();

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<FieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    public void setFieldInfos(List<FieldInfo> fieldInfos) {
        this.fieldInfos = fieldInfos;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
