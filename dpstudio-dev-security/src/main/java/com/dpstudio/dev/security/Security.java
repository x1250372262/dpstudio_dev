package com.dpstudio.dev.security;

import com.dpstudio.dev.security.bean.GroupBean;
import com.dpstudio.dev.security.bean.MenuBean;
import com.dpstudio.dev.security.bean.PermissionBean;
import com.dpstudio.dev.security.impl.DefaultModuleCfg;
import com.dpstudio.dev.security.init.MenuMeta;
import com.dpstudio.dev.security.init.PermissionMeta;
import com.dpstudio.dev.utils.ListUtils;
import com.dpstudio.dev.utils.ObjectUtils;
import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
            MenuMeta.init(__moduleCfg);
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

    @Override
    public List<MenuBean> menuList() {
        return MenuMeta.Store.get();
    }

    public List<MenuBean> permissionMenu() {
        List<MenuBean> menuBeanList = menuList();
        List<String> userPermissions = new ArrayList<>();
        boolean isFounder = false;
        IAuthenticator iAuthenticator = __moduleCfg.getAuthenticator();
        if (iAuthenticator == null) {
            throw new NullArgumentException("authenticator_class");
        }
        try {
            userPermissions = iAuthenticator.userPermission();
            isFounder = iAuthenticator.isFounder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(isFounder){
            return menuBeanList;
        }
        if (ObjectUtils.isEmpty(menuBeanList)) {
            return menuBeanList;
        }
        List<MenuBean> newMenuList = new ArrayList<>();
        for (MenuBean menuBean : menuBeanList) {
            if (StringUtils.isBlank(menuBean.permissions())) {
                newMenuList.add(menuBean);
                continue;
            }
            List<String> permissons = Arrays.asList(menuBean.getPermissions().split(","));;
            if (ObjectUtils.isEmpty(permissons)) {
                newMenuList.add(menuBean);
                continue;
            }
            if (ObjectUtils.isEmpty(userPermissions)) {
                continue;
            }
            if (ListUtils.checkSame(userPermissions, permissons)) {
                newMenuList.add(menuBean);
            }
        }
        return newMenuList;
    }


    public List<MenuBean> permissionMenu(boolean isFounder,List<String> userPermissions) {
        List<MenuBean> menuBeanList = menuList();
        if(isFounder){
            return menuBeanList;
        }
        if (ObjectUtils.isEmpty(menuBeanList)) {
            return menuBeanList;
        }
        List<MenuBean> newMenuList = new ArrayList<>();
        for (MenuBean menuBean : menuBeanList) {
            if (StringUtils.isBlank(menuBean.permissions())) {
                newMenuList.add(menuBean);
                continue;
            }
            List<String> permissons = Arrays.asList(menuBean.getPermissions().split(","));;
            if (ObjectUtils.isEmpty(permissons)) {
                newMenuList.add(menuBean);
                continue;
            }
            if (ObjectUtils.isEmpty(userPermissions)) {
                continue;
            }
            if (ListUtils.checkSame(userPermissions, permissons)) {
                newMenuList.add(menuBean);
            }
        }
        return newMenuList;
    }
}
