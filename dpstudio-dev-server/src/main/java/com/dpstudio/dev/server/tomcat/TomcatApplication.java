package com.dpstudio.dev.server.tomcat;

import com.dpstudio.dev.server.ServerConfig;
import com.dpstudio.dev.server.util.FileUtils;
import net.ymate.platform.webmvc.support.DispatchServlet;
import net.ymate.platform.webmvc.support.WebAppEventListener;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;

/**
 * @Author: mengxiang.
 * @Date: 2020/1/8.
 * @Time: 5:15 下午.
 * @Description:
 */
public class TomcatApplication {

    private static Tomcat tomcat = null;

    private boolean started = false;

    static {
        init();
    }

    private TomcatApplication() {

    }

    private static void init() {
        String tomcatBaseDir = FileUtils.createTempDir("tomcat_works_tempdir").getAbsolutePath();
        System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
        tomcat = new Tomcat();
        tomcat.setBaseDir(tomcatBaseDir);
        tomcat.setPort(ServerConfig.getPort());
        tomcat.setHostname(ServerConfig.getHostName());

        // 设置URI编码支持中文
        tomcat.getConnector().setURIEncoding(ServerConfig.CHARSET);
        ProtocolHandler handler = tomcat.getConnector().getProtocolHandler();
        if (handler instanceof AbstractProtocol) {
            AbstractProtocol<?> protocol = (AbstractProtocol<?>) handler;
            if (TomcatConfig.getMinSpareThreads() > 0) {
                protocol.setMinSpareThreads(TomcatConfig.getMinSpareThreads());
            }
            if (TomcatConfig.getMaxThreads() > 0) {
                protocol.setMaxThreads(TomcatConfig.getMaxThreads());
            }
            if (TomcatConfig.getConnectionTimeout() > 0) {
                protocol.setConnectionTimeout(TomcatConfig.getConnectionTimeout());
            }
            if (TomcatConfig.getMaxConnections() > 0) {
                protocol.setMaxConnections(TomcatConfig.getMaxConnections());
            }
            if (TomcatConfig.getAcceptCount() > 0) {
                protocol.setAcceptCount(TomcatConfig.getAcceptCount());
            }
        }

        Host host = tomcat.getHost();
        String workHome = "";
        if (StringUtils.isBlank(TomcatConfig.getWorkHome())) {
            String projectname = ServerConfig.getWorkName();
            if (StringUtils.isBlank(projectname)) {
                projectname = System.getProperty("user.dir");
                projectname = projectname.substring(projectname.lastIndexOf('/') + 1);
            }
            workHome = System.getProperty("user.dir").concat("/target/").concat(projectname).concat("/");
        } else {
            workHome = TomcatConfig.getWorkHome();
        }

        StandardContext context = (StandardContext) tomcat.addWebapp(host, ServerConfig.CONTEXT_PATH,
                workHome);
        ClassLoader classLoader = TomcatApplication.class.getClassLoader();
        context.setParentClassLoader(classLoader);
        context.addLifecycleListener(new Tomcat.FixContextListener());
        context.addApplicationEventListener(new WebAppEventListener());
        if (!TomcatConfig.isWebProject()) {
            tomcat.addServlet(ServerConfig.CONTEXT_PATH, "dispatchServlet", new DispatchServlet());
            context.addServletMappingDecoded("/*", "dispatchServlet");
        }
//        context.setJarScanner(new EmbededStandardJarScanner());
    }

    protected static TomcatApplication get() {
        return new TomcatApplication();
    }

    protected void startUp() throws Exception {
        if (tomcat == null) {
            init();
        }
        if (this.started) {
            return;
        }
        tomcat.start();
        this.started = true;
        // 注册关闭端口以进行关闭
        // 可以通过Socket关闭tomcat： telnet 127.0.0.1 8005，输入SHUTDOWN字符串
        tomcat.getServer().setPort(TomcatConfig.getCfgParamInt(TomcatConfig.SHUTDOWN_PORT));
        tomcat.getServer().await();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                stop();
            }
        });
    }

    private void stop() throws LifecycleException {
        System.out.println(" ymp stop  ... ");
        tomcat.stop();
    }
}
