package com.dpstudio.dev.server;


import com.dpstudio.dev.server.tomcat.TomcatServer;
import com.dpstudio.dev.server.undertow.UndertowServer;

/**
 * @Author: mengxiang.
 * @Date: 2020/1/8.
 * @Time: 5:27 下午.
 * @Description:
 */
public class Server {


    public static IServer get() {
        String type = ServerConfig.getType();
        switch (type) {
            case "tomcat":
                return new TomcatServer();
            case "undertow":
                return new UndertowServer();
            default:
                throw new IllegalStateException("不支持的服务类型: " + type);
        }
    }
}
