package com.dpstudio.dev.core.code;

/**
 * @Author: mengxiang.
 * @Date: 2019-01-15.
 * @Time: 15:22.
 * @Description:
 */
public enum C {

    /**
     * 通用错误码
     */
    SUCCESS(0, "操作成功"),
    ERROR(50000, "操作失败"),
    NO_DATA(50001, "数据不存在"),
    FIELDS_EXISTS(50002, "%s已存在"),
    NOT_LOGIN(50003, "用户未授权登录或会话已过期，请重新登录"),
    VERSION_NOT_SAME(50004, "数据版本不一致"),
    JWT_ERROR(50005, "jwt 签名错误或解析错误"),
    JWT_OUT_TIME(50006, "jwt 已经过期"),
    JWT_CREATE_ERROR(50007, "jwt token 生成失败");

    private final int code;
    private final String msg;

    C(int code, String msg) {
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
