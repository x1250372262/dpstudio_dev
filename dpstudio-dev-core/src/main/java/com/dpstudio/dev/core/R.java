package com.dpstudio.dev.core;

import com.dpstudio.dev.core.code.C;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mengxiang.
 * @Date: 2019-04-23.
 * @Time: 17:49.¬
 * @Description: 通用返回结果
 */
public class R implements Serializable {

    private int code;

    private String msg;

    private final Map<String, String> logMap = new HashMap<>();

    private Map<String, Object> attrs = new HashMap<String, Object>();

    private R() {
    }

    private R(int code) {
        this.code = code;
    }

    public R logIds(String key, String logId) {
        logMap.put(key, logId);
        return this;
    }

    public R logIds(String logId) {
        logMap.put("default", logId);
        return this;
    }

    public Map<String, String> logMap() {
        return logMap;
    }

    public static boolean check(R r) {
        return r.code() == C.SUCCESS.getCode();
    }

    public boolean check() {
        return this.code() == C.SUCCESS.getCode();
    }


    /**
     * 根据参数返回成功还是失败
     */
    public static R result(Object object) {
        if (object != null) {
            return R.ok();
        }
        return R.fail();
    }

    /**
     * 成功result
     */
    public static R ok() {
        return R.create(C.SUCCESS.getCode())
                .msg(C.SUCCESS.getMsg());
    }

    /**
     * 返回成功 并且带map参数
     */
    public static R mapResult(Map<String, Object> attrs) {
        return R.ok().attrs(attrs);
    }

    /**
     * 失败result
     */
    public static R fail() {
        return R.create(C.ERROR.getCode())
                .msg(C.ERROR.getMsg());
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
