package com.dpstudio.dev.mimiprogram;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/2.
 * @Time: 5:25 下午.
 * @Description:
 */
public enum ErrorCode {

    INIT_ERROR(51, "微信小程序模块初始化失败"),
    SESSION_ERROR(51, "session获取失败"),
    CHECK_USER_INFO_ERROR(52, "用户信息校验失败"),
    USER_INFO_ERROR(53, "用户信息获取失败"),
    PHONE_INFO_ERROR(54, "手机号信息获取失败");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
