package com.dpstudio.dev.core.code;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-01-15.
 * @Time: 15:22.
 * @Description:
 */
public enum CommonCode {

    /**
     * 通用错误码
     */
    COMMON_OPTION_SUCCESS(0, "操作成功"),
    COMMON_OPTION_ERROR(50000, "操作失败"),
    COMMON_OPTION_NO_DATA(50001, "数据不存在"),
    COMMON_NAME_EXISTS(50002, "名称已存在");

    private int code;
    private String msg;

    CommonCode(int code, String msg) {
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
