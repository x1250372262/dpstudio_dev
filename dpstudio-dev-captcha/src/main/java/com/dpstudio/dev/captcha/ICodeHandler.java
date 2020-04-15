package com.dpstudio.dev.captcha;

import com.dpstudio.dev.core.CommonResult;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/14.
 * @Time: 11:41 上午.
 * @Description:
 */
public interface ICodeHandler {

    /**
     * 验证是否可以发送验证码
     * @return
     * @throws Exception
     */
    CommonResult allowSendCode(String mobile) throws Exception;

}
