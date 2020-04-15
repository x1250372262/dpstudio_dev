package com.dpstudio.dev.captcha.web;

import com.dpstudio.dev.captcha.service.ICaptchaService;
import net.ymate.module.captcha.Captcha;
import net.ymate.module.captcha.CaptchaTokenBean;
import net.ymate.module.captcha.ICaptcha;
import net.ymate.module.captcha.web.intercept.CaptchaStatusInterceptor;
import net.ymate.platform.core.beans.annotation.Before;
import net.ymate.platform.core.beans.annotation.Inject;
import net.ymate.platform.validation.validate.VLength;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.BinaryView;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/14.
 * @Time: 11:31 上午.
 * @Description:
 */
@Controller
@RequestMapping("/dpstudio/captcha")
@Before(CaptchaStatusInterceptor.class)
public class CaptchaController {


    private String __doCaptchaBase64(String contentType, ByteArrayOutputStream outputStream) {
        return "data:" + contentType + ";base64," + Base64.encodeBase64String(outputStream.toByteArray());
    }

    @Inject
    private ICaptchaService iCaptchaService;

    /**
     * 图片验证码 完全使用ymp本身的验证码模块  没有变动
     *
     * @param scope 作用域标识，用于区分不同客户端及数据存储范围
     * @param type  输出类型，取值范围：空|data|json，当type=data或type=json时采用Base64编码输出图片
     * @return 返回生成的验证码图片
     * @throws Exception 可能产生的任何异常
     */
    @RequestMapping("/")
    public IView create(@VLength(max = 32) @RequestParam String scope,

                        @RequestParam String type) throws Exception {

        ICaptcha _captcha = Captcha.get();
        ByteArrayOutputStream _output = new ByteArrayOutputStream();
        CaptchaTokenBean _bean = _captcha.generate(scope, _output);
        String _contentType = "image/" + _captcha.getModuleCfg().getFormat();
        if (StringUtils.equalsIgnoreCase(type, "data")) {
            return View.textView(__doCaptchaBase64(_contentType, _output));
        } else if (StringUtils.equalsIgnoreCase(type, "json")) {
            return WebResult.succeed()
                    .dataAttr("scope", _bean.getScope())
                    .dataAttr("captcha", __doCaptchaBase64(_contentType, _output)).toJSON();
        }
        return new BinaryView(new ByteArrayInputStream(_output.toByteArray()), _output.size()).setContentType(_contentType);
    }

    /**
     * 短信验证码
     *
     * @param scope  作用域标识，用于区分不同客户端及数据存储范围
     * @param mobile 手机号码
     * @return 发送手机短信验证码
     * @throws Exception 可能产生的任何异常
     */
    @RequestMapping(value = "/sms_code", method = Type.HttpMethod.POST)
    public IView sms(@VLength(max = 32) @RequestParam String scope,

                     @VRequired @RequestParam String mobile) throws Exception {

        return iCaptchaService.send(ICaptcha.Type.SMS, scope, mobile);
    }

    /**
     * @param scope  作用域标识，用于区分不同客户端及数据存储范围
     * @param target 目标(当验证手机号码或邮件地址时使用)
     * @param token  预验证的令牌值
     * @return 返回判断token是否匹配的验证结果（主要用于客户端验证）
     * @throws Exception 可能产生的任何异常
     */
    @RequestMapping(value = "/match", method = {Type.HttpMethod.GET, Type.HttpMethod.POST})
    public IView match(@VLength(max = 32) @RequestParam String scope,

                       @VLength(max = 50) @RequestParam String target,

                       @VLength(max = 10) @RequestParam String token) throws Exception {

        return WebResult.formatView(WebResult.succeed()
                .dataAttr("matched", ICaptcha.Status.MATCHED.equals(Captcha.get().validate(scope, target, token, false))), "json");
    }
}
