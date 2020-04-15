package com.dpstudio.dev.module;

import net.ymate.module.captcha.CaptchaTokenBean;
import net.ymate.platform.core.YMP;

/**
 * ICaptcha
 * <br>
 * Code generation by yMateScaffold on 2020/04/14 上午 11:14
 *
 * @author ymatescaffold
 * @version 1.0
 */
public interface ICaptcha {

    String MODULE_NAME = "dpstudio.captcha";

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回模块配置对象
     */
    ICaptchaModuleCfg getModuleCfg();

    /**
    * @return 返回模块是否已初始化
    */
    boolean isInited();

    ICodeHandler getCodeHandler();

    /**
     * 获取验证码令牌对象
     *
     * @param type   验证码类型
     * @param scope  作用域标识，用于区分不同客户端及数据存储范围
     * @param target 目标(手机号码或邮件地址)
     * @return 返回验证码令牌对象若不存在或已过期则重新生成
     * @throws Exception 可能产生的任何异常
     */
    CaptchaTokenBean getCaptchaToken(net.ymate.module.captcha.ICaptcha.Type type, String scope, String target) throws Exception;


}