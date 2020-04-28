package com.dpstudio.dev.security;

import com.dpstudio.dev.security.bean.GroupBean;
import com.dpstudio.dev.security.bean.PermissionBean;
import com.dpstudio.dev.security.impl.DefaultModuleCfg;
import com.dpstudio.dev.security.init.PermissionMeta;
import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Security
 * <br>
 * Code generation by yMateScaffold on 2019/05/05 下午 16:04
 *
 * @author ymatescaffold
 * @version 1.0
 */
@Module
public class Security implements IModule, ISecurity {

    private static final Log _LOG = LogFactory.getLog(Security.class);

    public static final Version VERSION = new Version(1, 0, 0, Security.class.getPackage().getImplementationVersion(), Version.VersionType.Alphal);

    private static volatile ISecurity __instance;

    private YMP __owner;

    private ISecurityModuleCfg __moduleCfg;

    private boolean __inited;

    public static ISecurity get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(Security.class);
                }
            }
        }
        return __instance;
    }

    @Override
    public String getName() {
        return ISecurity.MODULE_NAME;
    }

    @Override
    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing juncheng-module-security-" + VERSION);
            //
            __owner = owner;
            __moduleCfg = new DefaultModuleCfg(owner);
            PermissionMeta.init(__moduleCfg);
            __inited = true;
        }
    }

    @Override
    public boolean isInited() {
        return __inited;
    }

    @Override
    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            PermissionMeta.destroy();
            __moduleCfg = null;
            __owner = null;
        }
    }

    @Override
    public YMP getOwner() {
        return __owner;
    }

    @Override
    public ISecurityModuleCfg getModuleCfg() {
        return __moduleCfg;
    }

    /**
     * 获取组列表
     *
     * @return
     */
    @Override
    public List<GroupBean> getGroupList() {
        return PermissionMeta.getGroups();
    }

    /**
     * 获取权限列表
     *
     * @return
     */
    @Override
    public List<PermissionBean> getPermissonList(String groupId) {
        return PermissionMeta.getPermissions(groupId);
    }
}
