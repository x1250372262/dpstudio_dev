package com.dpstudio.dev.security.impl;

import com.dpstudio.dev.security.IAuthenticator;
import com.dpstudio.dev.security.ISecurity;
import com.dpstudio.dev.security.ISecurityModuleCfg;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.support.IConfigReader;
import net.ymate.platform.core.support.impl.MapSafeConfigReader;
import net.ymate.platform.core.util.RuntimeUtils;
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

    private String menuFilePath;

    private IAuthenticator authenticator;

    private boolean enabled;

    public DefaultModuleCfg(YMP owner) {
        IConfigReader _moduleCfgs = MapSafeConfigReader.bind(owner.getConfig().getModuleConfigs(ISecurity.MODULE_NAME));
        packageName = _moduleCfgs.getString("package_name");
        menuFilePath = _moduleCfgs.getString("menu_file_path", "${root}/menu/menu.xml");
        enabled = _moduleCfgs.getBoolean("enabled",false);
        authenticator = _moduleCfgs.getClassImpl("authenticator_class", IAuthenticator.class);
        if (authenticator == null && enabled) {
            throw new NullArgumentException("authenticator_class");
        }
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
    public boolean enabled() {
        return enabled;
    }

    @Override
    public String menuFilePath() {
        return menuFilePath;
    }
}