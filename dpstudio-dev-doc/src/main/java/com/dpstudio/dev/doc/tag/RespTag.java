package com.dpstudio.dev.doc.tag;

import com.dpstudio.dev.doc.Constants;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: Resp注释的内容封装
 */
public class RespTag extends AbstractDocTag<String> {

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 参数描述
     */
    private String paramDesc;

    /**
     * 是否必填,默认false
     */
    private boolean require;

    /**
     * 类型
     */
    private String paramType;

    /**
     * 示例值
     */
    private String demoValue;


    public RespTag() {
    }

    public RespTag(String tagName, String paramName, String paramDesc, String paramType, boolean require, String demoValue) {
        super(tagName);
        this.paramName = paramName;
        this.paramDesc = paramDesc;
        this.paramType = paramType;
        this.require = require;
        this.demoValue = demoValue;
    }

    @Override
    public String getValues() {
        String s = paramName + " " + paramDesc;
        if (StringUtils.isNotBlank(paramType)) {
            s += " " + paramType;
        }
        s += " " + (require ? Constants.YES_EN : Constants.NOT_EN);
        return s;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public boolean isRequire() {
        return require;
    }

    public void setRequire(boolean require) {
        this.require = require;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getDemoValue() {
        return demoValue;
    }

    public void setDemoValue(String demoValue) {
        this.demoValue = demoValue;
    }
}
