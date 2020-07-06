package com.dpstudio.dev.doc.core.model;

import java.util.List;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 字段信息
 */
public class FieldInfo {

    private String name;

    private Class<?> type;

    private String simpleTypeName;

    private String comment;

    private String demoValue;

    /**
     * 是否必填,默认false
     */
    private boolean require;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getSimpleTypeName() {
        return simpleTypeName;
    }

    public void setSimpleTypeName(String simpleTypeName) {
        this.simpleTypeName = simpleTypeName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isRequire() {
        return require;
    }

    public void setRequire(boolean require) {
        this.require = require;
    }

    public String getDemoValue() {
        return demoValue;
    }

    public void setDemoValue(String demoValue) {
        this.demoValue = demoValue;
    }
}
