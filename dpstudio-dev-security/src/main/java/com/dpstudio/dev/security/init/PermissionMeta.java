package com.dpstudio.dev.security.init;

import com.dpstudio.dev.security.ISecurity;
import com.dpstudio.dev.security.ISecurityModuleCfg;
import com.dpstudio.dev.security.annotation.Group;
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


    private static final Log LOG = LogFactory.getLog(PermissionMeta.class);

    //权限组
    private static final Map<String, List<GroupBean>> GROUP_CACHES;
    //权限
    private static final Map<String, List<PermissionBean>> PERMISSIONS_CACHES;

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
        LOG.info("权限列表收集成功");
    }

    /**
     * 销毁权限列表
     */
    public static void destroy() {
        GROUP_CACHES.clear();
        PERMISSIONS_CACHES.clear();
        LOG.info("权限列表销毁成功");
    }


    /**
     * 获取权限列表
     */
    public static List<PermissionBean> getPermissions(String groupId) {
        if (StringUtils.isNotBlank(groupId)) {
            return PERMISSIONS_CACHES.get(ISecurity.CacheKey.PERMISSIONS_CACHE_KEY.name())
                    .stream().filter(permissionBean -> groupId.equals(permissionBean.getGroupId())).collect(Collectors.toList());
        }
        return PERMISSIONS_CACHES.get(ISecurity.CacheKey.PERMISSIONS_CACHE_KEY.name());
    }

    /**
     * 根据code获取权限
     */
    public static PermissionBean findByCode( List<PermissionBean> permissionBeans,String code) {
        if(permissionBeans == null){
            permissionBeans = getPermissions(null);
        }
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return permissionBeans.stream().filter(permissionBean -> code.equals(permissionBean.getCode())).findFirst().get();
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
     * 获取权限组列表
     *
     * @return
     */
    public static List<GroupBean> getGroups(String level) {
        if(StringUtils.isNotBlank(level)){
            return GROUP_CACHES.get(ISecurity.CacheKey.GROUP_CACHE_KEY.name())
                    .stream().filter(groupBean -> level.equals(groupBean.getLevel())).collect(Collectors.toList());
        }
        return getGroups();
    }

    /**
     * 获取权限信息
     *
     * @return
     */
    private static void create(ISecurityModuleCfg moduleCfg) {
        if (moduleCfg.enabled()) {
            String packageName = moduleCfg.getPackageName();

            Reflections reflections = new Reflections(packageName);
            Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(Security.class);
            List<PermissionBean> permissionBeans = new ArrayList<>();
            List<GroupBean> groupBeans = new ArrayList<>();
            for (Class<?> classes : classesList) {
                //得到该类下面的所有方法
                Method[] methods = classes.getDeclaredMethods();
                for (Method method : methods) {
                    //得到该类下面的Group注解
                    Group group = method.getAnnotation(Group.class);
                    if (null == group) {
                        continue;
                    }
                    Permission[] permissions = group.permissions();
                    if (permissions.length <= 0) {
                        continue;
                    }
                    for (Permission permission : permissions) {
                        //添加权限列表
                        Optional<PermissionBean> permissionBean = permissionBeans.stream().filter(p -> p.getCode().equals(permission.code())).findFirst();
                        if (!permissionBean.isPresent()) {
                            permissionBeans.add(new PermissionBean(permission.name(), permission.code(), permission.groupId(), permission.groupName(),group.level()));
                        }
                        //添加组列表
                        Optional<GroupBean> groupBean = groupBeans.stream().filter(gb -> gb.getId().equals(permission.groupId())).findFirst();
                        if (!groupBean.isPresent()) {
                            groupBeans.add(new GroupBean(permission.groupName(), permission.groupId(),group.level()));
                        }
                    }
                }
            }
            GROUP_CACHES.put(ISecurity.CacheKey.GROUP_CACHE_KEY.name(), groupBeans);
            PERMISSIONS_CACHES.put(ISecurity.CacheKey.PERMISSIONS_CACHE_KEY.name(), permissionBeans);
        }
    }
}
