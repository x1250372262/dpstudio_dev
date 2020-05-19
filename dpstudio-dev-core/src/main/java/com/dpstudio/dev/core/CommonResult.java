package com.dpstudio.dev.core;

import com.dpstudio.dev.core.code.CommonCode;
import net.ymate.platform.persistence.IResultSet;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-04-23.
 * @Time: 17:49.
 * @Description: 通用返回结果
 */
public class CommonResult implements Serializable {

    private int code;

    private String msg;

    private Map<String, Object> attrs = new HashMap<String, Object>();

    private CommonResult() {
    }

    private CommonResult(int code) {
        this.code = code;
    }

    /**
     * 根据参数返回成功还是失败
     * @param object
     * @return
     */
    public static CommonResult toResult(Object object) {
        if (object != null) {
            return CommonResult.successResult();
        }
        return CommonResult.errorResult();
    }

    /**
     * 查询列表
     * @param resultSet
     * @param page
     * @return
     */
    public static IView listView(IResultSet resultSet,int page){
        return WebResult.succeed().data(resultSet.getResultData())
                .attr("total", resultSet.getRecordCount())
                .attr("page", page).toJSON();
    }

    /**
     * 查询列表
     * @param resultSet
     * @return
     */
    public static IView listView(IResultSet resultSet){
        return WebResult.succeed().data(resultSet.getResultData()).toJSON();
    }

    /**
     * 成功result
     * @return
     */
    public static CommonResult successResult() {
        return CommonResult.create(CommonCode.COMMON_OPTION_SUCCESS.getCode())
                .msg(CommonCode.COMMON_OPTION_SUCCESS.getMsg());
    }

    /**
     * 返回成功 并且带map参数
     * @return
     */
    public static CommonResult mapResult(Map<String, Object> attrs) {
        return CommonResult.successResult().attrs(attrs);
    }

    /**
     * 失败result
     * @return
     */
    public static CommonResult errorResult() {
        return CommonResult.create(CommonCode.COMMON_OPTION_ERROR.getCode())
                .msg(CommonCode.COMMON_OPTION_ERROR.getMsg());
    }

    /**
     * 成功view
     * @return
     */
    public static IView successView() {
        return CommonResult.successResult().toWebResult().toJSON();
    }

    /**
     * 失败view
     * @return
     */
    public static IView errorView() {
        return CommonResult.errorResult().toWebResult().toJSON();
    }

    /**
     * 转换成webresult
     * @return
     */
    public WebResult toWebResult() {
        if (attrs != null) {
            return WebResult.create(code).msg(msg).attrs(attrs);
        }
        return WebResult.create(code).msg(msg);
    }

    /**
     * 转换成webresult视图
     * @return
     */
    public IView toJsonView() {
        if (attrs != null) {
            return WebResult.create(code).msg(msg).attrs(attrs).toJSON();
        }
        return WebResult.create(code).msg(msg).toJSON();
    }

    public int code() {
        return code;
    }

    public static CommonResult create() {
        return new CommonResult();
    }

    public static CommonResult create(int code) {
        return new CommonResult(code);
    }

    public CommonResult code(int code) {
        this.code = code;
        return this;
    }

    public String msg() {
        return StringUtils.trimToEmpty(msg);
    }

    public CommonResult msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Map<String, Object> attrs() {
        return attrs;
    }

    @SuppressWarnings("unchecked")
    public <T> T attr(String attrKey) {
        return (T) this.attrs.get(attrKey);
    }

    public CommonResult attr(String attrKey, Object attrValue) {
        this.attrs.put(attrKey, attrValue);
        return this;
    }

    public CommonResult attr(String attrKey, Object attrValue,Object defaultValue) {
        this.attrs.put(attrKey, ObjectUtils.defaultIfNull(attrValue,defaultValue));
        return this;
    }

    public CommonResult attrs(Map<String, Object> attrs) {
        this.attrs = attrs;
        return this;
    }

}
