package com.dpstudio.dev.core;

import com.dpstudio.dev.core.code.C;
import com.dpstudio.dev.log.LR;
import net.ymate.platform.persistence.IResultSet;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-04-23.
 * @Time: 17:49.¬
 * @Description: 通用返回结果
 */
public class R implements Serializable {

    private int code;

    private String msg;

    private LR lr;

    private Map<String, Object> attrs = new HashMap<String, Object>();

    private R() {
    }

    private R(int code) {
        this.code = code;
    }

    public R lr(LR lr) {
        this.lr = lr;
        return this;
    }

    public LR lr() {
        return this.lr;
    }

    public static boolean check(R r) {
        return r.code() == C.SUCCESS.getCode();
    }

    public boolean check() {
        return this.code() == C.SUCCESS.getCode();
    }

    public static boolean checkVersion(Object var1,Object var2){
        return Objects.equals(var1,var2);
    }

    /**
     * 根据参数返回成功还是失败
     *
     * @param object
     * @return
     */
    public static R result(Object object) {
        if (object != null) {
            return R.ok();
        }
        return R.fail();
    }

    /**
     * 查询列表
     *
     * @param resultSet
     * @param page
     * @return
     */
    public static IView listView(IResultSet resultSet, int page) {
        return WebResult.succeed().data(resultSet.getResultData())
                .attr("total", resultSet.getRecordCount())
                .attr("page", page).keepNullValue().toJSON();
    }

    /**
     * 查询列表
     *
     * @param resultSet
     * @return
     */
    public static IView listView(IResultSet resultSet) {
        return WebResult.succeed().data(resultSet.getResultData()).keepNullValue().toJSON();
    }

    /**
     * 成功result
     *
     * @return
     */
    public static R ok() {
        return R.create(C.SUCCESS.getCode())
                .msg(C.SUCCESS.getMsg());
    }

    /**
     * 返回成功 并且带map参数
     *
     * @return
     */
    public static R mapResult(Map<String, Object> attrs) {
        return R.ok().attrs(attrs);
    }

    /**
     * 失败result
     *
     * @return
     */
    public static R fail() {
        return R.create(C.ERROR.getCode())
                .msg(C.ERROR.getMsg());
    }

    /**
     * 成功view
     *
     * @return
     */
    public static IView okJson() {
        return R.ok().webResult().toJSON();
    }

    /**
     * 失败view
     *
     * @return
     */
    public static IView failJson() {
        return R.fail().webResult().toJSON();
    }

    /**
     * 转换成webresult
     *
     * @return
     */
    public WebResult webResult() {
        if (attrs != null) {
            return WebResult.create(code).msg(msg).attrs(attrs);
        }
        return WebResult.create(code).msg(msg);
    }

    /**
     * 转换成webresult视图
     *
     * @return
     */
    public IView json() {
        if (attrs != null) {
            return WebResult.create(code).msg(msg).attrs(attrs).toJSON();
        }
        return WebResult.create(code).msg(msg).toJSON();
    }

    public int code() {
        return code;
    }

    public static R create() {
        return new R();
    }

    public static R create(int code) {
        return new R(code);
    }

    public R code(int code) {
        this.code = code;
        return this;
    }

    public String msg() {
        return StringUtils.trimToEmpty(msg);
    }

    public R msg(String msg) {
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

    public R attr(String attrKey, Object attrValue) {
        this.attrs.put(attrKey, attrValue);
        return this;
    }

    public R attr(String attrKey, Object attrValue, Object defaultValue) {
        this.attrs.put(attrKey, ObjectUtils.defaultIfNull(attrValue, defaultValue));
        return this;
    }

    public R attrs(Map<String, Object> attrs) {
        this.attrs = attrs;
        return this;
    }

}
