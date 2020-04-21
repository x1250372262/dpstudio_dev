package com.dpstudio.dev.captcha.service.impl;

import com.dpstudio.dev.core.CommonResult;
import com.dpstudio.dev.core.code.CommonCode;
import com.dpstudio.dev.captcha.Captcha;
import com.dpstudio.dev.captcha.ICodeHandler;
import com.dpstudio.dev.captcha.service.ICaptchaService;
import net.ymate.module.captcha.CaptchaTokenBean;
import net.ymate.module.captcha.ICaptcha;
import net.ymate.platform.core.beans.annotation.Bean;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.log.Logs;
import net.ymate.platform.webmvc.util.ErrorCode;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/2.
 * @Time: 4:51 下午.
 * @Description:
 */
@Bean
public class CaptchaServiceImpl implements ICaptchaService {

    private IView doCaptchaSend(ICaptcha.Type type, String scope, String target) throws Exception {
        CaptchaTokenBean _tokenBean = Captcha.get().getCaptchaToken(type, scope, target);
        if (_tokenBean != null) {
            try {
                if (net.ymate.module.captcha.Captcha.get().captchaSend(type, scope, _tokenBean)) {
                    return WebResult.formatView(WebResult.succeed(), "json");
                }
                return WebResult.formatView(WebResult.create(ErrorCode.REQUEST_OPERATION_FORBIDDEN), "json");
            } catch (Exception e) {
                Logs.get().getLogger().warn("An exception occurred at send to " + target, RuntimeUtils.unwrapThrow(e));
            }
        }
        return WebResult.formatView(WebResult.create(ErrorCode.INTERNAL_SYSTEM_ERROR), "json");
    }

    @Override
    public IView send(ICaptcha.Type type, String scope, String target,String attach) throws Exception {
                ICodeHandler iCodeHandler = Captcha.get().getCodeHandler();
                //可以发送验证码
                if (iCodeHandler == null) {
                    return doCaptchaSend(type, scope, target);
                } else {
                    CommonResult commonResult = iCodeHandler.allowSendCode(target,attach);
            if (commonResult == null || commonResult.code() == CommonCode.COMMON_OPTION_SUCCESS.getCode()) {
                return doCaptchaSend(type, scope, target);
            }
            return commonResult.toJsonView();
        }
    }
}
