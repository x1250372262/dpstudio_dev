package com.dpstudio.dev.mimiprogram.web;

import com.dpstudio.dev.core.CommonResult;
import com.dpstudio.dev.mimiprogram.service.IWxMimiProgramUserService;
import net.ymate.platform.core.beans.annotation.Inject;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.view.IView;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/2.
 * @Time: 4:46 下午.
 * @Description: 小程序用户接口
 */
@Controller
@RequestMapping("/dpstudio/wx/mimiprogram/user")
public class WxMimiProgramUserController {

    @Inject
    private IWxMimiProgramUserService iWxMimiProgramUserService;

    /**
     * 授权
     *
     * @param scope        授权类型
     * @param code
     * @param rawData
     * @param signature
     * @param encrypteData
     * @param iv
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "oauth", method = Type.HttpMethod.POST)
    public IView login(@RequestParam(defaultValue = "base") String scope,
                       @VRequired(msg = "code不能为空")
                       @RequestParam String code,
                       @VRequired(msg = "用户非敏感信息不能为空")
                       @RequestParam(value = "rawData") String rawData,
                       @VRequired(msg = "签名不能为空")
                       @RequestParam(value = "signature") String signature,
                       @VRequired(msg = "用户敏感信息不能为空")
                       @RequestParam(value = "encrypteData") String encrypteData,
                       @VRequired(msg = "解密算法的向量不能为空")
                       @RequestParam(value = "iv") String iv) throws Exception {
        CommonResult commonResult = iWxMimiProgramUserService.oauth(scope, code, rawData, signature, encrypteData, iv);
        return commonResult.toJsonView();

    }
}
