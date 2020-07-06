package com.dpstudio.dev.core.code;

/**
 * @Author: 徐建鹏.
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
    NAME_EXISTS(50002, "名称已存在"),
    NOT_LOGIN(50003, "用户未授权登录或会话已过期，请重新登录");

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
