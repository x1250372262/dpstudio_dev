package com.dpstudio.dev.security.init;

import com.dpstudio.dev.security.ISecurityModuleCfg;
import com.dpstudio.dev.security.bean.MenuBean;
import com.dpstudio.dev.security.exception.MenuException;
import com.dpstudio.dev.security.utils.Objects;
import net.ymate.platform.configuration.IConfigFileParser;
import net.ymate.platform.configuration.impl.XMLConfigFileParser;
import net.ymate.platform.core.util.RuntimeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-01-17.
 * @Time: 08:24.
 * @Description:
 */
public class MenuMeta {


    public static class Store {

        public static List<MenuBean> MENU_BEANS  = new ArrayList<>();
        public static void set(List<MenuBean> menuBeans) {
            MENU_BEANS = menuBeans;
        }
        public static List<MenuBean> get(){
            return MENU_BEANS;
        }
    }

//    public static class Store {
//        private static ThreadLocal<List<MenuBean>> menuLocal = new ThreadLocal<>();
//
//        public static List<MenuBean> get() {
//            return menuLocal.get();
//        }
//
//        public static void set(List<MenuBean> menuBeans) {
//            menuLocal.set(menuBeans);
//        }
//    }

    private static final Log _LOG = LogFactory.getLog(MenuMeta.class);

    private MenuMeta() {
    }

    /**
     * 创建权限列表
     */
    public static void init(ISecurityModuleCfg moduleCfg) {
        try {
            List<MenuBean> menuList = menuList(moduleCfg);
            Store.set(menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        _LOG.info("惨淡收集成功");
    }

    private static List<MenuBean> menuList(ISecurityModuleCfg moduleCfg) throws Exception {
        List<MenuBean> menuList = new ArrayList<>();
        File file = new File(RuntimeUtils.replaceEnvVariable(moduleCfg.menuFilePath()));
        if (!file.exists()) {
            throw new MenuException("菜单配置文件不存在");
        }
        IConfigFileParser handler = new XMLConfigFileParser(file).load(true);
        Map<String, IConfigFileParser.Category> categoryMap = handler.getCategories();
        categoryMap.forEach((k, v) -> {
            Map<String, IConfigFileParser.Property> propertyMap = handler.getCategory(v.getName()).getPropertyMap();
            propertyMap.forEach((k1, v1) -> {
                MenuBean menuBean = MenuBean.builder()
                        .id(Objects.get(v1.getAttribute("id"), ""))
                        .name(StringUtils.defaultIfBlank(v1.getName(), ""))
                        .value(StringUtils.defaultIfBlank(v1.getContent(), ""))
                        .icon(Objects.get(v1.getAttribute("icon"), ""))
                        .url(Objects.get(v1.getAttribute("url"), ""))
                        .pid(Objects.get(v1.getAttribute("pid"), ""))
                        .permissions(Objects.get(v1.getAttribute("permissions"), ""));
                menuList.add(menuBean);
            });
        });
        return menuList;
    }

}
