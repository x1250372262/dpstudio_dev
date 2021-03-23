package com.dpstudio.dev.test;

import net.ymate.platform.commons.json.IJsonObjectWrapper;
import net.ymate.platform.commons.json.JsonWrapper;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: mengxiang.
 * @create: 2021-01-06 16:19
 * @Description:
 */
public class MvcResult {

    private String content;

    private int ret;

    private String msg;

    private int httpCode;

    private String httpMsg;

    private IJsonObjectWrapper jsonObjectWrapper;

    public static MvcResult create() {
        return new MvcResult();
    }

    public MvcResult content(String content) {
        this.content = content;
        return this;
    }

    public MvcResult ret(int ret) {
        this.ret = ret;
        return this;
    }

    public MvcResult msg(String msg) {
        this.msg = msg;
        return this;
    }

    public MvcResult httpCode(int httpCode) {
        this.httpCode = httpCode;
        return this;
    }

    public MvcResult httpMsg(String httpMsg) {
        this.httpMsg = httpMsg;
        return this;
    }

    public int httpCode() {
        return this.httpCode;
    }

    public String httpMsg() {
        return this.httpMsg;
    }

    public IJsonObjectWrapper json() {
        IJsonObjectWrapper json = JsonWrapper.createJsonObject();
        json.put("httpCode", httpCode);
        json.put("httpMsg", httpMsg);
        if (StringUtils.isBlank(content)) {
            json.put("body", "");
            return json;
        }
        IJsonObjectWrapper bodyJson = JsonWrapper.fromJson(content).getAsJsonObject();
        json.put("body", bodyJson);
        return json;
    }

    public String jsonStr() {
        IJsonObjectWrapper json = JsonWrapper.createJsonObject();
        json.put("httpCode", httpCode);
        json.put("httpMsg", httpMsg);
        if (StringUtils.isBlank(content)) {
            json.put("body", "");
            return json.toString(true, true);
        }
        IJsonObjectWrapper bodyJson = JsonWrapper.fromJson(content).getAsJsonObject();
        json.put("body", bodyJson);
        return json.toString(true, true);
    }

    public String text() {
        return "http状态码:" + httpCode + "---说明文字:" + httpMsg + "---返回报文:" + content;
    }

    public IJsonObjectWrapper originJson() {
        if (StringUtils.isBlank(content)) {
            return JsonWrapper.createJsonObject();
        }
        return JsonWrapper.fromJson(content).getAsJsonObject();
    }

    public String originJsonStr() {
        if (StringUtils.isBlank(content)) {
            return "";
        }
        return JsonWrapper.fromJson(content).getAsJsonObject().toString(true, true);
    }


    public String originContent() {
        return this.content;
    }

    public int ret() {
        return jsonObjectWrapper.getInt("ret");
    }

    public String msg() {
        return jsonObjectWrapper.getString("msg");
    }

}
