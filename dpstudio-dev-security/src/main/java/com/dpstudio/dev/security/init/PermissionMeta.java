package com.dpstudio.dev.security.init;

import com.dpstudio.dev.security.ISecurity;
import com.dpstudio.dev.security.ISecurityModuleCfg;
import com.dpstudio.dev.security.annotation.Permission;
import com.dpstudio.dev.security.annotation.Security;
import com.dpstudio.dev.security.bean.GroupBean;
import com.dpstudio.dev.security.bean.PermissionBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-01-17.
 * @Time: 08:24.
 * @Description:
 */
public class PermissionMeta {


    private static final Log _LOG = LogFactory.getLog(PermissionMeta.class);

    //权限组
    private static Map<String, List<GroupBean>> GROUP_CACHES;
    //权限
    private static Map<String, List<PermissionBean>> PERMISSIONS_CACHES;

    static {
        GROUP_CACHES = new ConcurrentHashMap<>();
        PERMISSIONS_CACHES = new ConcurrentHashMap<>();
    }

    private PermissionMeta() {
    }

    /**
     * 创建权限列表
     */
    public static void init(ISecurityModuleCfg moduleCfg) {
        create(moduleCfg);
        _LOG.info("权限列表收集成功");
    }

    /**
     * 销毁权限列表
     */
    public static void destroy() {
        GROUP_CACHES.clear();
        PERMISSIONS_CACHES.clear();
        _LOG.info("权限列表销毁成功");
    }


    /**
     * 获取权限列表
     */
    public static List<PermissionBean> getPermissions(String groupId) {
        if (StringUtils.isNotBlank(groupId)) {
            return PERMISSIONS_CACHES.get(ISecurity.CacheKey.PERMISSIONS_CACHE_KEY.name())
                    .stream().filter(groupBean -> groupId.equals(groupBean.getGroupId())).collect(Collectors.toList());
        }
        return PERMISSIONS_CACHES.get(ISecurity.CacheKey.PERMISSIONS_CACHE_KEY.name());
    }

    /**
     * 获取权限组列表
     *
     * @return
     */
    public static List<GroupBean> getGroups() {
        return GROUP_CACHES.get(ISecurity.CacheKey.GROUP_CACHE_KEY.name());
    }

    /**
     * 获取权限信息
     *
     * @return
     */
    private static void create(ISecurityModuleCfg moduleCfg) {
        if (!moduleCfg.isDisabled()) {
            String packageName = moduleCfg.getPackageName();

            Reflections reflections = new Reflections(packageName);
            Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(Security.class);
            List<PermissionBean> permissionBeans = new ArrayList<>();
            List<GroupBean> groupBeans = new ArrayList<>();
            for (Class classes : classesList) {
                //得到该类下面的所有方法
                Method[] methods = classes.getDeclaredMethods();
                for (Method method : methods) {
                    //得到该类下面的Rpermission注解
                    Permission permission = method.getAnnotation(Permission.class);
                    if (null != permission) {
                        //添加权限列表
                        permissionBeans.add(new PermissionBean(permission.name(), permission.code(), permission.groupId()));
                        //添加组列表
                        Optional<GroupBean> groupBean = groupBeans.stream().filter(gb -> gb.getName().equals(permission.groupName())).findFirst();
                        if (!groupBean.isPresent()) {
                            groupBeans.add(new GroupBean(permission.groupName(), permission.groupId()));
                        }
                    }
                }
            }
            GROUP_CACHES.put(ISecurity.CacheKey.GROUP_CACHE_KEY.name(), groupBeans);
            PERMISSIONS_CACHES.put(ISecurity.CacheKey.PERMISSIONS_CACHE_KEY.name(), permissionBeans);
        }
    }
}