package com.dpstudio.dev.server;


import net.ymate.platform.commons.lang.BlurObject;
import net.ymate.platform.core.IApplication;
import net.ymate.platform.core.YMP;

/**
 * @Author: mengxiang.
 * @Date: 2020/1/8.
 * @Time: 5:20 下午.
 * @Description:
 */
public class ServerConfig {

    public static String PORT = "server.port";
    public static String TYPE = "server.type";
    public static String WORK_NAME = "server.work_name";
    public static String HOST_NAME = "server.host_name";

    private static final int port;
    private static final String hostName;
    private static final String type;
    private static final String workName;

    //默认项目路径
    public static final String CONTEXT_PATH = "";
    //默认编码
    public static final String CHARSET = "UTF-8";

    static {
        IApplication iApplication = YMP.get();
        hostName = iApplication.getParam(HOST_NAME, "localhost");
        type = iApplication.getParam(TYPE, "tomcat");
        port = BlurObject.bind(iApplication.getParam(PORT, "8080")).toIntValue();
        workName = iApplication.getParam(WORK_NAME);
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


}
