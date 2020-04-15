package com.dpstudio.dev.captcha.impl;

import com.dpstudio.dev.captcha.ICaptcha;
import com.dpstudio.dev.captcha.ICaptchaModuleCfg;
import com.dpstudio.dev.captcha.ICodeGenerator;
import com.dpstudio.dev.captcha.ICodeHandler;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.support.IConfigReader;
import net.ymate.platform.core.support.impl.MapSafeConfigReader;
import net.ymate.platform.core.util.ClassUtils;
import org.apache.commons.lang.StringUtils;

/**
 * ICaptcha
 * <br>
 * Code generation by yMateScaffold on 2020/04/14 上午 11:14
 *
 * @author ymatescaffold
 * @version 1.0
 */
public class DefaultCaptchaModuleCfg implements ICaptchaModuleCfg {

    private ICodeGenerator iCodeGenerator;

    private ICodeHandler iCodeHandler;

    public DefaultCaptchaModuleCfg(YMP owner) {
        IConfigReader moduleCfg = MapSafeConfigReader.bind(owner.getConfig().getModuleConfigs(ICaptcha.MODULE_NAME));
        String codeGeneratorClass = moduleCfg.getString(CODE_GENERATOR_CLASS);
        if (StringUtils.isNotBlank(codeGeneratorClass)) {
            iCodeGenerator = ClassUtils.impl(codeGeneratorClass, ICodeGenerator.class, this.getClass());
        }
        if(iCodeGenerator == null){
            iCodeGenerator = new DefaultCodeGeneratorImpl();
        }

        String codeHandlerClass = moduleCfg.getString(CODE_HANDLER_CLASS);
        if (StringUtils.isNotBlank(codeHandlerClass)) {
            iCodeHandler = ClassUtils.impl(codeHandlerClass, ICodeHandler.class, this.getClass());
        }
    }

    @Override
    public ICodeGenerator codeGenerator() {
        return iCodeGenerator;
    }

    @Override
    public ICodeHandler codeHandler() {
        return iCodeHandler;
    }
}