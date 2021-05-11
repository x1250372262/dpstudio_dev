package com.mx.dev.server;


import net.ymate.platform.commons.lang.BlurObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mengxiang.
 * @Date: 2020/1/8.
 * @Time: 5:20 下午.
 * @Description:
 */
public class ServerConfig {

    public static String PORT = "port";
    public static String TYPE = "type";
    public static String WORK_NAME = "work_name";
    public static String HOST_NAME = "host_name";

    private static int port;
    private static String hostName;
    private static String type;
    private static String workName;

    //默认项目路径
    public static final String CONTEXT_PATH = "";
    //默认编码
    public static final String CHARSET = "UTF-8";

    private static final String MODULE_NAME = "server";

    public static Map<String, String> moduleCfgs;

    static {
        moduleCfgs = Config.getModuleCfg(MODULE_NAME);
        init();
    }

    private static void init(){
        hostName = getCfgParam(HOST_NAME,"localhost");
        type = getCfgParam(TYPE,"tomcat");
        port = getCfgParamInt(PORT,8080);
        workName = getCfgParam(WORK_NAME);
    }

    public static String getHostName() {
        return hostName;
    }


    public static int getPort() {
        return port;
    }

    public static String getType() {
        return type;
    }

    public static String getWorkName() {
        return workName;
    }

    public static String getCfgParam(String prefix) {
        return moduleCfgs.get(prefix);
    }

    public static Map<String, String> getModuleCfg(String prefix) {
        Map<String, String> params = new HashMap<>();
        for (Object key : moduleCfgs.keySet()) {
            if (StringUtils.startsWith((String) key, prefix)) {
                String cfgKey = StringUtils.substring((String) key, prefix.length()+1);
                String cfgValue = moduleCfgs.get(key);
                params.put(cfgKey, cfgValue);
            }
        }
        return params;
    }

    public static String getCfgParam(String prefix, String defaultValue) {
        return moduleCfgs.get(prefix) != null ? moduleCfgs.get(prefix) : defaultValue;
    }

    public static int getCfgParamInt(String prefix, int defaultValue) {
        return moduleCfgs.get(prefix) != null ? BlurObject.bind(moduleCfgs.get(prefix)).toIntValue() : defaultValue;
    }

    public static int getCfgParamInt(String prefix) {
        return moduleCfgs.get(prefix) != null ? BlurObject.bind(moduleCfgs.get(prefix)).toIntValue() : 0;
    }

    public static double getCfgParamDouble(String prefix, double defaultValue) {
        return moduleCfgs.get(prefix) != null ? BlurObject.bind(moduleCfgs.get(prefix)).toDoubleValue() : defaultValue;
    }

    public static double getCfgParamDouble(String prefix) {
        return moduleCfgs.get(prefix) != null ? BlurObject.bind(moduleCfgs.get(prefix)).toDoubleValue() : 0D;
    }

    public static long getCfgParamLong(String prefix, long defaultValue) {
        return moduleCfgs.get(prefix) != null ? BlurObject.bind(moduleCfgs.get(prefix)).toLongValue() : defaultValue;
    }

    public static long getCfgParamLong(String prefix) {
        return moduleCfgs.get(prefix) != null ? BlurObject.bind(moduleCfgs.get(prefix)).toLongValue() : 0L;
    }

    public static boolean getCfgParamBool(String prefix, boolean defaultValue) {
        return moduleCfgs.get(prefix) != null ? BlurObject.bind(moduleCfgs.get(prefix)).toBooleanValue() : defaultValue;
    }

    public static boolean getCfgParamBool(String prefix) {
        return moduleCfgs.get(prefix) != null && BlurObject.bind(moduleCfgs.get(prefix)).toBooleanValue();
    }


}
