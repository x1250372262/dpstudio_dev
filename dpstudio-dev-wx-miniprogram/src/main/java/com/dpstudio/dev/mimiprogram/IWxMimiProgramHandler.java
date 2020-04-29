package com.dpstudio.dev.mimiprogram;

import com.dpstudio.dev.mimiprogram.bean.WxPhoneInfo;
import com.dpstudio.dev.mimiprogram.bean.WxUserInfo;
import com.dpstudio.dev.core.CommonResult;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/2.
 * @Time: 5:31 下午.
 * @Description: 微信小程序数据处理
 */
public interface IWxMimiProgramHandler {

    /**
     * 处理用户信息
     *
     * @param wxUserInfo
     * @return
     * @throws Exception
     */
    CommonResult handlerUserData(WxUserInfo wxUserInfo,String attach) throws Exception;

    /**
     * 处理手机号信息
     *
     * @param token
     * @param wxPhoneInfo
     * @return
     * @throws Exception
     */
    CommonResult handlerMobileData(String attach,String token,WxPhoneInfo wxPhoneInfo) throws Exception;
}
