package com.dpstudio.dev.captcha.service;

import net.ymate.module.captcha.ICaptcha;
import net.ymate.platform.webmvc.view.IView;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/2.
 * @Time: 4:51 下午.
 * @Description:
 */
public interface ICaptchaService {

    /**
     * 发送验证码
     *
     * @param type
     * @param scope
     * @param target
     * @return
     * @throws Exception
     */
    IView send(ICaptcha.Type type, String scope, String target) throws Exception;
}
