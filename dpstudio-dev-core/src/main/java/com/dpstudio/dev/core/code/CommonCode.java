package com.dpstudio.dev.core.code;

import net.ymate.platform.webmvc.util.ErrorCode;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-01-15.
 * @Time: 15:22.
 * @Description:
 */
public class CommonCode {


    //通用
    public static final ErrorCode COMMON_OPTION_SUCCESS = new ErrorCode(ErrorCode.SUCCEED, "操作成功");
    public static final ErrorCode COMMON_OPTION_ERROR = new ErrorCode(50000, "操作失败");


}
