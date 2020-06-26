package com.dpstudio.dev.generator.bean;

import net.ymate.platform.core.util.ClassUtils;

/**
 * @Author: 徐建鹏.
 * @Date: 2019/11/5.
 * @Time: 12:00 下午.
 * @Description: 根据ymp框架仿写
 */
public class Attr {

    private String varType;

    private String varName;

    private String columnName;

    private boolean autoIncrement;

    private boolean signed;

    private final int precision;

    private final int scale;

    private final boolean nullable;

    private final String defaultValue;

    private final String remarks;

    private boolean readonly;

    public Attr(String varType, String varName, String columnName, boolean autoIncrement, boolean signed, int precision, int scale, boolean nullable, String defaultValue, String remarks) {
        this.varName = varName;
        this.varType = varType;
        this.columnName = columnName;
        this.autoIncrement = autoIncrement;
        this.signed = signed;
        try {
            if (!signed && !ClassUtils.isSubclassOf(Class.forName(varType), Number.class)) {
                this.signed = true;
            }
        } catch (Exception ignored) {
        }
        this.precision = precision;
        this.scale = scale;
        this.nullable = nullable;
        this.defaultValue = defaultValue;
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return this.getVarName();
    }

    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public boolean isNullable() {
        return nullable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
}
