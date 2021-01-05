package com.dpstudio.dev.server;

import net.ymate.platform.core.beans.annotation.Ignored;
import net.ymate.platform.core.support.IInitialization;

@Ignored
public interface IServerConfig extends IInitialization<IServer> {

    String PORT = "port";
    String TYPE = "type";
    String WORK_NAME = "work_name";
    String HOST_NAME = "host_name";
    String SHUTDOWN_PORT = "shutdown_port";
    String MAX_THREADS = "max_threads";
    String MIN_SPARE_THREADS = "min_spare_threads";
    String CONNECTION_TIMEOUT = "connection_Timeout";
    String MAX_CONNECTIONS = "max_connections";
    String ACCEPT_COUNT = "accept_count";
    String WORK_HOME = "work_home";
    String IS_WEB = "is_web";

    int port();

    String type();

    String workName();

    String hostName();

    /**
     * tomcat配置
     */
    int maxThreads();

    int minSpareThreads();

    int connectionTimeout();

    int maxConnections();

    int acceptCount();

    int shutDownPort();

    String workHome();

    boolean webProject();

}