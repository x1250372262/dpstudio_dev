package com.dpstudio.dev.mimiprogram.impl;

import com.dpstudio.dev.mimiprogram.IWxMimiProgram;
import com.dpstudio.dev.mimiprogram.IWxMimiProgramHandler;
import com.dpstudio.dev.mimiprogram.IWxMimiProgramModuleCfg;
import com.dpstudio.dev.mimiprogram.IWxMimiProgramPayHandler;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.support.IConfigReader;
import net.ymate.platform.core.support.impl.MapSafeConfigReader;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

/**
 * Default implement for interface IDemoModuleCfg
 */
public class DefaultWxMimiProgramModuleCfg implements IWxMimiProgramModuleCfg {


    private String appId;

    private String appSecret;

    private String qrCodePath;

    private String qrCodeFormat;

    private IWxMimiProgramHandler iWxMimiProgramHandler;

    private IWxMimiProgramPayHandler iWxMimiProgramPayHandler;

    private boolean defaultHandlerByDatabase;

    public DefaultWxMimiProgramModuleCfg(YMP owner) {
        IConfigReader moduleCfg = MapSafeConfigReader.bind(owner.getConfig().getModuleConfigs(IWxMimiProgram.MODULE_NAME));
        appId = StringUtils.trimToEmpty(moduleCfg.getString(APP_ID));
        appSecret = StringUtils.trimToEmpty(moduleCfg.getString(APP_SECRET));
        qrCodePath = RuntimeUtils.replaceEnvVariable(StringUtils.defaultIfBlank(moduleCfg.getString(QRCODE_PATH), "${root}/qr_path"));
        qrCodeFormat = StringUtils.defaultIfBlank(moduleCfg.getString(QRCODE_FORMAT), "jpg");
        String dataHandlerClass = moduleCfg.getString(DATA_HANDLER_CLASS);
        if (StringUtils.isNotBlank(dataHandlerClass)) {
            iWxMimiProgramHandler = ClassUtils.impl(dataHandlerClass, IWxMimiProgramHandler.class, this.getClass());
        }
        if (iWxMimiProgramHandler == null) {
            iWxMimiProgramHandler = new DefaultWxMimiProgramHandler();
        }
        defaultHandlerByDatabase = moduleCfg.getBoolean(DATA_HANDLER_DEFAULT_TYPE, false);


        String wxMimiProgramPayHandlerClass = moduleCfg.getString(PAY_HANDLER_CLASS);
        if (StringUtils.isNotBlank(wxMimiProgramPayHandlerClass)) {
            iWxMimiProgramPayHandler = ClassUtils.impl(wxMimiProgramPayHandlerClass, IWxMimiProgramPayHandler.class, this.getClass());
        }
        if (iWxMimiProgramPayHandler == null) {
            throw new NullArgumentException("pay_handler_class");
        }
    }

    @Override
    public boolean defaultHandlerByDatabase() {
        return defaultHandlerByDatabase;
    }

    @Override
    public IWxMimiProgramHandler iWxMimiProgramHandler() {
        return iWxMimiProgramHandler;
    }

    @Override
    public String appId() {
        return appId;
    }

    @Override
    public String appSecret() {
        return appSecret;
    }

    @Override
    public String qrCodePath() {
        return qrCodePath;
    }

    @Override
    public String qrCodeFormat() {
        return qrCodeFormat;
    }

    @Override
    public IWxMimiProgramPayHandler iWxMimiProgramPayHandler() {
        return iWxMimiProgramPayHandler;
    }


}