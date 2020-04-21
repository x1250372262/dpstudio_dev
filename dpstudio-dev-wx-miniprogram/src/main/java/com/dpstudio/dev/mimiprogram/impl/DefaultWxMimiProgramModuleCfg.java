package com.dpstudio.dev.mimiprogram.impl;

import com.dpstudio.dev.mimiprogram.IWxMimiProgram;
import com.dpstudio.dev.mimiprogram.IWxMimiProgramHandler;
import com.dpstudio.dev.mimiprogram.IWxMimiProgramModuleCfg;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.support.IConfigReader;
import net.ymate.platform.core.support.impl.MapSafeConfigReader;
import net.ymate.platform.core.util.ClassUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Default implement for interface IDemoModuleCfg
 */
public class DefaultWxMimiProgramModuleCfg implements IWxMimiProgramModuleCfg {


    private String appId;

    private String appSecret;

    private IWxMimiProgramHandler iWxMimiProgramHandler;

    private boolean defaultHandlerByDatabase;

    public DefaultWxMimiProgramModuleCfg(YMP owner) {
        IConfigReader moduleCfg = MapSafeConfigReader.bind(owner.getConfig().getModuleConfigs(IWxMimiProgram.MODULE_NAME));
        appId = StringUtils.trimToEmpty(moduleCfg.getString(APP_ID));
        appSecret = StringUtils.trimToEmpty(moduleCfg.getString(APP_SECRET));
        String dataHandlerClass = moduleCfg.getString(DATA_HANDLER_CLASS);
        if (StringUtils.isNotBlank(dataHandlerClass)) {
            iWxMimiProgramHandler = ClassUtils.impl(dataHandlerClass, IWxMimiProgramHandler.class, this.getClass());
        }
        if (iWxMimiProgramHandler == null) {
            iWxMimiProgramHandler = new DefaultWxMimiProgramHandler();
        }
        defaultHandlerByDatabase = moduleCfg.getBoolean(DATA_HANDLER_DEFAULT_TYPE,false);
    }

    @Override
    public boolean defaultHandlerByDatabase() {
        return defaultHandlerByDatabase;
    }

    @Override
    public IWxMimiProgramHandler handler() {
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
}
