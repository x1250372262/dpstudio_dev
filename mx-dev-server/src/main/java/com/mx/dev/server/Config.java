package com.dpstudio.dev.server;

import net.ymate.platform.commons.util.RuntimeUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: mengxiang.
 * @Date: 2020/1/9.
 * @Time: 5:44 下午.
 * @Description:
 */
public class Config {

    private static final Properties props = new Properties();

    static {
        load();
    }

    private static void load() {
        InputStream in = null;
        try {
            String env = System.getProperty("server.env");
            String prefix = "server";
            if (StringUtils.isNotBlank(env)) {
                if (StringUtils.equalsIgnoreCase(env, "dev")) {
                    prefix = "server_dev";
                } else if (StringUtils.equalsIgnoreCase(env, "test")) {
                    prefix = "server_test";
                } else if (StringUtils.equalsIgnoreCase(env, "prod")) {
                    prefix = "server_prod";
                }
            }
            ClassLoader classLoader = Config.class.getClassLoader();
            in = classLoader.getResourceAsStream(prefix + ".properties");
            if (in == null) {
                if (RuntimeUtils.isWindows()) {
                    in = classLoader.getResourceAsStream(prefix + "_WIN.properties");
                } else if (RuntimeUtils.isUnixOrLinux()) {
                    in = classLoader.getResourceAsStream(prefix + "_UNIX.properties");
                }
            }
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public static Map<String, String> getModuleCfg(String moduleName) {
        Map<String, String> params = new HashMap<>();
        for (Object key : props.keySet()) {
            String prefix = "ymp.".concat(moduleName).concat(".");
            if (StringUtils.startsWith((String) key, prefix)) {
                String cfgKey = StringUtils.substring((String) key, prefix.length()).replace("dp.cloud.", "");
                String cfgValue = props.getProperty((String) key);
                params.put(cfgKey, cfgValue);
            }
        }
        return params;
    }

}
