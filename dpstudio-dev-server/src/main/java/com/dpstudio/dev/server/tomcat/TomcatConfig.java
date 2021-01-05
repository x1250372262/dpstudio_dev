package com.dpstudio.dev.server.tomcat;

import com.dpstudio.dev.server.ServerConfig;
import net.ymate.platform.commons.lang.BlurObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @Author: mengxiang.
 * @Date: 2020/1/8.
 * @Time: 5:20 下午.
 * @Description:
 */
public class TomcatConfig {

    public static String PORT = "server.port";
    public static String TYPE = "server.type";
    public static String WORK_NAME = "server.work_name";
    public static String HOST_NAME = "server.host_name";
    public static String SHUTDOWN_PORT = "shutdown_port";
    public static String MAX_THREADS = "max_threads";
    public static String MIN_SPARE_THREADS = "min_spare_threads";
    public static String CONNECTION_TIMEOUT = "connection_Timeout";
    public static String MAX_CONNECTIONS = "max_connections";
    public static String ACCEPT_COUNT = "accept_count";
    public static String WORK_HOME = "work_home";
    public static String IS_WEB = "is_web";

    private static int maxThreads;
    private static int minSpareThreads;
    private static int connectionTimeout;
    private static int maxConnections;
    private static int acceptCount;
    private static int shutDownPort;
    private static String workHome;
    private static boolean webProject;

    private static final String PREFIX = "tomcat";

    private static final Map<String, String> moduleCfgs;

    static {
        moduleCfgs = ServerConfig.getModuleCfg(PREFIX);
        init();
    }

    private static void init() {
        maxThreads = getCfgParamInt(MAX_THREADS);
        minSpareThreads = getCfgParamInt(MIN_SPARE_THREADS);
        connectionTimeout = getCfgParamInt(CONNECTION_TIMEOUT);
        maxConnections = getCfgParamInt(MAX_CONNECTIONS);
        acceptCount = getCfgParamInt(ACCEPT_COUNT);
        shutDownPort = getCfgParamInt(SHUTDOWN_PORT, "8005");
        workHome = getCfgParam(WORK_HOME);
        webProject = getCfgParamBool(IS_WEB, "true");
    }


    public static int getMaxThreads() {
        return maxThreads;
    }

    public static int getMinSpareThreads() {
        return minSpareThreads;
    }

    public static int getConnectionTimeout() {
        return connectionTimeout;
    }

    public static int getMaxConnections() {
        return maxConnections;
    }

    public static int getAcceptCount() {
        return acceptCount;
    }

    public static int getShutDownPort() {
        return shutDownPort;
    }

    public static void setShutDownPort(int shutDownPort) {
        TomcatConfig.shutDownPort = shutDownPort;
    }

    private static String getValue(String prefix) {
        return moduleCfgs.get(prefix);
    }

    public static String getCfgParam(String prefix) {
        return moduleCfgs.get(prefix);
    }

    public static String getCfgParam(String prefix, String defaultValue) {
        return StringUtils.defaultIfBlank(getValue(prefix), defaultValue);
    }

    public static int getCfgParamInt(String prefix, String defaultValue) {
        return BlurObject.bind(StringUtils.defaultIfBlank(getValue(prefix), defaultValue)).toIntValue();
    }

    public static int getCfgParamInt(String prefix) {
        return StringUtils.isNotBlank(getValue(prefix)) ? BlurObject.bind(getValue(prefix)).toIntValue() : 0;
    }

    public static double getCfgParamDouble(String prefix, String defaultValue) {
        return BlurObject.bind(StringUtils.defaultIfBlank(getValue(prefix), defaultValue)).toDoubleValue();
    }

    public static double getCfgParamDouble(String prefix) {
        return StringUtils.isNotBlank(getValue(prefix)) ? BlurObject.bind(getValue(prefix)).toDoubleValue() : 0D;
    }

    public static long getCfgParamLong(String prefix, String defaultValue) {
        return BlurObject.bind(StringUtils.defaultIfBlank(getValue(prefix), defaultValue)).toLongValue();
    }

    public static long getCfgParamLong(String prefix) {
        return StringUtils.isNotBlank(getValue(prefix)) ? BlurObject.bind(getValue(prefix)).toLongValue() : 0L;
    }

    public static boolean getCfgParamBool(String prefix, String defaultValue) {
        return BlurObject.bind(StringUtils.defaultIfBlank(getValue(prefix), defaultValue)).toBooleanValue();
    }

    public static boolean getCfgParamBool(String prefix) {
        return StringUtils.isNotBlank(getValue(prefix)) && BlurObject.bind(getValue(prefix)).toBooleanValue();
    }

    public static String getWorkHome() {
        return workHome;
    }

    public static boolean isWebProject() {
        return webProject;
    }
}
