package com.dpstudio.dev.mimiprogram.bean;


import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-02-26.
 * @Time: 18:04.
 * @Description: 微信用户绑定的手机号相关信息
 */
public class WxPhoneInfo implements Serializable {

    /**
     * 用户绑定的手机号（国外手机号会有区号）
     */
    private String phoneNumber;
    /**
     * 没有区号的手机号
     */
    private String purePhoneNumber;
    /**
     * 区号
     */
    private String countryCode;
    private Watermark watermark;

    public static WxPhoneInfo byJson(String json) {
        return JSONObject.parseObject(json, WxPhoneInfo.class);
    }

    public static class Watermark {
        private String timestamp;
        private String appid;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Watermark getWatermark() {
        return watermark;
    }

    public void setWatermark(Watermark watermark) {
        this.watermark = watermark;
    }
}
