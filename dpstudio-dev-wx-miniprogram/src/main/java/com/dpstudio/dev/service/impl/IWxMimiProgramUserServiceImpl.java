package com.dpstudio.dev.service.impl;

import com.dpstudio.dev.ErrorCode;
import com.dpstudio.dev.IWxMimiProgram;
import com.dpstudio.dev.WxMimiProgram;
import com.dpstudio.dev.bean.WxCodeSession;
import com.dpstudio.dev.bean.WxPhoneInfo;
import com.dpstudio.dev.bean.WxUserInfo;
import com.dpstudio.dev.core.CommonResult;
import com.dpstudio.dev.service.IWxMimiProgramUserService;
import net.ymate.platform.core.beans.annotation.Bean;
import org.apache.commons.lang.StringUtils;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/2.
 * @Time: 4:51 下午.
 * @Description:
 */
@Bean
public class IWxMimiProgramUserServiceImpl implements IWxMimiProgramUserService {

    private final static String SCOPE_BASE = "base";
    private final static String SCOPE_INFO = "userInfo";

    @Override
    public CommonResult oauth(String scope, String code, String rawData, String signature, String encrypteData, String iv) throws Exception {

        scope = StringUtils.defaultIfBlank(scope, SCOPE_BASE);

        IWxMimiProgram iWxMimiProgram = WxMimiProgram.get();
        if (iWxMimiProgram == null) {
            return CommonResult.create(ErrorCode.INIT_ERROR.getCode()).msg(ErrorCode.INIT_ERROR.getMsg());
        }
        WxCodeSession wxCodeSession = iWxMimiProgram.getSessionInfo(code);
        if (wxCodeSession == null) {
            return CommonResult.create(ErrorCode.SESSION_ERROR.getCode()).msg(ErrorCode.SESSION_ERROR.getMsg());
        }
        // 用户信息校验
        if (!iWxMimiProgram.checkUserInfo(wxCodeSession.getSessionKey(), rawData, signature)) {
            return CommonResult.create(ErrorCode.CHECK_USER_INFO_ERROR.getCode()).msg(ErrorCode.CHECK_USER_INFO_ERROR.getMsg());
        }
        WxUserInfo wxUserInfo = iWxMimiProgram.getUserInfo(wxCodeSession.getSessionKey(), encrypteData, iv);
        if (wxUserInfo == null) {
            return CommonResult.create(ErrorCode.USER_INFO_ERROR.getCode()).msg(ErrorCode.USER_INFO_ERROR.getMsg());
        }

        CommonResult commonResult;
        if (SCOPE_INFO.equals(scope)) {
            WxPhoneInfo wxPhoneInfo = iWxMimiProgram.getPhoneNoInfo(wxCodeSession.getSessionKey(), encrypteData, iv);
            if (wxPhoneInfo == null) {
                return CommonResult.create(ErrorCode.PHONE_INFO_ERROR.getCode()).msg(ErrorCode.PHONE_INFO_ERROR.getMsg());
            }
            commonResult = iWxMimiProgram.getHandler().handlerUserData(wxUserInfo, wxPhoneInfo);
        } else {
            commonResult = iWxMimiProgram.getHandler().handlerUserData(wxUserInfo);
        }

        return CommonResult.create(commonResult.code()).msg(commonResult.msg()).attrs(commonResult.attrs());

    }
}
