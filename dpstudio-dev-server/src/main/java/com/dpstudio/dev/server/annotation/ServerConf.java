package com.dpstudio.dev.server.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServerConf {

    /**
     * @return 服务端口
     */
    int port() default 8080;

    /**
     * @return 主机名称
     */
    String hostName() default "localhost";

    /**
     * @return 服务类型
     */
    String type() default "tomcat";

    /**
     * @return 工作目录
     */
    String workName();

    /**
     * tomcat配置
     *
     * @return
     */

    int tomcatMaxThreads();

    int tomcatMinSpareThreads();

    int tomcatConnectionTimeout();

    int tomcatMaxConnections();

    int tomcatAcceptCount();

    int tomcatShutDownPort();

    int tomcatWorkHome();

    int tomcatWebProject();


}
