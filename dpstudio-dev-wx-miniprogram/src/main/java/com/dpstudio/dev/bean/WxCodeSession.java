package com.dpstudio.dev.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-02-26.
 * @Time: 18:04.
 * @Description: 微信登录session信息
 */
public class WxCodeSession implements Serializable {

    @JSONField(name = "session_key")
    private String sessionKey;

    @JSONField(name = "openid")
    private String openId;

    @JSONField(name = "unionid")
    private String unionId;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }


    public static WxCodeSession byJson(String json) {
        return JSONObject.parseObject(json, WxCodeSession.class);
    }
}
