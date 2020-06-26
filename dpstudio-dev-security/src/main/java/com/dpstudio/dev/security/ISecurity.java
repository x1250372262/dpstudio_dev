package com.dpstudio.dev.security;

import com.alibaba.fastjson.JSONArray;
import com.dpstudio.dev.security.bean.GroupBean;
import com.dpstudio.dev.security.bean.MenuBean;
import com.dpstudio.dev.security.bean.PermissionBean;
import net.ymate.platform.core.YMP;

import java.util.List;

/**
 * ISecurity
 * <br>
 * Code generation by yMateScaffold on 2019/05/05 下午 16:04
 *
 * @author ymatescaffold
 * @version 1.0
 */
public interface ISecurity {

    /**
     * 权限key
     */
    enum CacheKey {
        GROUP_CACHE_KEY, PERMISSIONS_CACHE_KEY
    }

    String MODULE_NAME = "dpstudio.security";

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回模块配置对象
     */
    ISecurityModuleCfg getModuleCfg();

    /**
     * @return 返回模块是否已初始化
     */
    boolean isInited();

    /**
     * 获取组列表
     *
     * @return
     */
    List<GroupBean> getGroupList();

    /**
     * 获取权限列表
     *
     * @return
     */
    List<PermissionBean> getPermissonList(String groupId);

    /**
     * 菜单列表
     *
     * @return
     */
    List<MenuBean> menuList();

    /**
     * 带权限的菜单
     *
     * @return
     */
    List<MenuBean> permissionMenu();

    /**
     * 带权限的菜单
     * @param isFounder 是否总管理
     * @param userPermissions 拥有的权限
     * @return
     */
    List<MenuBean> permissionMenu(boolean isFounder,List<String> userPermissions);

}