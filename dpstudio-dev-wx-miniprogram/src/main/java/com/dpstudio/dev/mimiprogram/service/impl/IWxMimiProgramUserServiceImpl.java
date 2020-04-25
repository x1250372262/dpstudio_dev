package com.dpstudio.dev.mimiprogram.service.impl;

import com.dpstudio.dev.core.CommonResult;
import com.dpstudio.dev.mimiprogram.ErrorCode;
import com.dpstudio.dev.mimiprogram.IWxMimiProgram;
import com.dpstudio.dev.mimiprogram.WxMimiProgram;
import com.dpstudio.dev.mimiprogram.bean.WxCodeSession;
import com.dpstudio.dev.mimiprogram.bean.WxPhoneInfo;
import com.dpstudio.dev.mimiprogram.bean.WxUserInfo;
import com.dpstudio.dev.mimiprogram.service.IWxMimiProgramUserService;
import net.ymate.platform.core.beans.annotation.Bean;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/2.
 * @Time: 4:51 下午.
 * @Description:
 */
@Bean
public class IWxMimiProgramUserServiceImpl implements IWxMimiProgramUserService {

    @Override
    public CommonResult getSession(String code) throws Exception {
        IWxMimiProgram iWxMimiProgram = WxMimiProgram.get();
        if (iWxMimiProgram == null) {
            return CommonResult.create(ErrorCode.INIT_ERROR.getCode()).msg(ErrorCode.INIT_ERROR.getMsg());
        }
        WxCodeSession wxCodeSession = iWxMimiProgram.getSessionInfo(code);
        if (wxCodeSession == null) {
            return CommonResult.create(ErrorCode.SESSION_ERROR.getCode()).msg(ErrorCode.SESSION_ERROR.getMsg());
        }
        return CommonResult.successResult().attr("data", wxCodeSession);
    }

    @Override
    public CommonResult userInfo(String sessionKey, String rawData, String signature, String encrypteData, String iv) throws Exception {

        IWxMimiProgram iWxMimiProgram = WxMimiProgram.get();
        if (iWxMimiProgram == null) {
            return CommonResult.create(ErrorCode.INIT_ERROR.getCode()).msg(ErrorCode.INIT_ERROR.getMsg());
        }
        // 用户信息校验
        if (!iWxMimiProgram.checkUserInfo(sessionKey, rawData, signature)) {
            return CommonResult.create(ErrorCode.CHECK_USER_INFO_ERROR.getCode()).msg(ErrorCode.CHECK_USER_INFO_ERROR.getMsg());
        }
        WxUserInfo wxUserInfo = iWxMimiProgram.getUserInfo(sessionKey, encrypteData, iv);
        if (wxUserInfo == null) {
            return CommonResult.create(ErrorCode.USER_INFO_ERROR.getCode()).msg(ErrorCode.USER_INFO_ERROR.getMsg());
        }

        CommonResult commonResult = iWxMimiProgram.getHandler().handlerUserData(wxUserInfo);
        return CommonResult.create(commonResult.code()).msg(commonResult.msg()).attrs(commonResult.attrs());

    }

    @Override
    public CommonResult mobileInfo(String token,String sessionKey, String encryptedData, String ivStr) throws Exception {
        IWxMimiProgram iWxMimiProgram = WxMimiProgram.get();
        if (iWxMimiProgram == null) {
            return CommonResult.create(ErrorCode.INIT_ERROR.getCode()).msg(ErrorCode.INIT_ERROR.getMsg());
        }
        WxPhoneInfo wxPhoneInfo = iWxMimiProgram.getPhoneNoInfo(sessionKey, encryptedData, ivStr);
        if (wxPhoneInfo == null) {
            return CommonResult.create(ErrorCode.USER_INFO_ERROR.getCode()).msg(ErrorCode.USER_INFO_ERROR.getMsg());
        }
        CommonResult commonResult = iWxMimiProgram.getHandler().handlerMobileData(token,wxPhoneInfo);
        return CommonResult.create(commonResult.code()).msg(commonResult.msg()).attrs(commonResult.attrs());
    }
}
