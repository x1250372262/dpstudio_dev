package com.dpstudio.dev.captcha;

/**
 * ICaptcha
 * <br>
 * Code generation by yMateScaffold on 2020/04/14 上午 11:14
 *
 * @author ymatescaffold
 * @version 1.0
 */
public interface ICaptchaModuleCfg {

    /**
     * 验证码生成方式  不适用于图形验证码
     */
    String CODE_GENERATOR_CLASS = "code_generator_class";

    /**
     * codd处理逻辑
     */
    String CODE_HANDLER_CLASS = "code_handler_class";

    ICodeGenerator codeGenerator();

    ICodeHandler codeHandler();
}