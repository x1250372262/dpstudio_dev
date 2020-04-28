package com.dpstudio.dev.security.impl;

import com.dpstudio.dev.security.IAuthenticator;
import com.dpstudio.dev.security.ISecurity;
import com.dpstudio.dev.security.ISecurityModuleCfg;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.support.IConfigReader;
import net.ymate.platform.core.support.impl.MapSafeConfigReader;
import org.apache.commons.lang.NullArgumentException;

/**
 * ISecurity
 * <br>
 * Code generation by yMateScaffold on 2019/05/05 下午 16:04
 *
 * @author ymatescaffold
 * @version 1.0
 */
public class DefaultModuleCfg implements ISecurityModuleCfg {

    private String packageName;

    private IAuthenticator authenticator;

    private boolean isDisabled;

    public DefaultModuleCfg(YMP owner) {
        IConfigReader _moduleCfgs = MapSafeConfigReader.bind(owner.getConfig().getModuleConfigs(ISecurity.MODULE_NAME));
        packageName = _moduleCfgs.getString("package_name");
        authenticator = _moduleCfgs.getClassImpl("authenticator_class", IAuthenticator.class);
        if (authenticator == null) {
            throw new NullArgumentException("authenticator_class");
        }
        isDisabled = _moduleCfgs.getBoolean("disabled");
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public IAuthenticator getAuthenticator() {
        return authenticator;
    }

    @Override
    public boolean isDisabled() {
        return isDisabled;
    }
}