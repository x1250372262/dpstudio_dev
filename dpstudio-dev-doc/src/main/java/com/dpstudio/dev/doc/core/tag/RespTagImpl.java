package com.dpstudio.dev.doc.core.tag;

import com.dpstudio.dev.doc.core.utils.Constant;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: Resp注释的内容封装
 */
public class RespTagImpl extends DocTag<String> {

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

    public RespTagImpl(String tagName, String paramName, String paramDesc, String paramType, boolean require) {
        super(tagName);
        this.paramName = paramName;
        this.paramDesc = paramDesc;
        this.paramType = paramType;
        this.require = require;
    }

    @Override
    public String getValues() {
        String s = paramName + " " + paramDesc;
        if (StringUtils.isNotBlank(paramType)) {
            s += " " + paramType;
        }
        s += " " + (require ? Constant.YES_ZH : Constant.NOT_ZH);
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
}
