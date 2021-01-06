package com.dpstudio.dev.test.server;

import com.dpstudio.dev.test.MVC;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.jsp.HackInstanceManager;
import io.undertow.jsp.JspServletBuilder;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ErrorPage;
import io.undertow.servlet.api.ListenerInfo;
import net.ymate.platform.webmvc.support.DispatchFilter;
import net.ymate.platform.webmvc.support.WebAppEventListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.jasper.deploy.JspPropertyGroup;
import org.apache.jasper.deploy.TagLibraryInfo;

import javax.servlet.DispatcherType;
import java.io.File;
import java.util.HashMap;

/**
 * @Author: mengxiang.
 * @Date: 2020/1/8.
 * @Time: 5:15 下午.
 * @Description: undertow服务器  暂时不支持jsp页面带有标签的
 */
public class UndertowServer {


    private static Undertow undertow = null;


    static {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResourceManager getResourceManager() {
        String projectname = "";
        String workHome = System.getProperty("user.dir");
        if (StringUtils.isBlank(projectname)) {
            projectname = workHome.substring(workHome.lastIndexOf('/') + 1);
        }
        return new FileResourceManager(new File(workHome.concat("/target/").concat(projectname)), 1024, false);
    }


    private static void init() throws Exception {
        DeploymentInfo deploymentInfo = Servlets.deployment();
        deploymentInfo.setDeploymentName("ymp");
        deploymentInfo.setResourceManager(getResourceManager());
        deploymentInfo.setClassLoader(Undertow.class.getClassLoader());
        deploymentInfo.setContextPath("/");
        deploymentInfo.addListener(new ListenerInfo(WebAppEventListener.class));
        deploymentInfo.setEagerFilterInit(true);        // 启动时初始化 filter
        deploymentInfo.addFilter(
                Servlets.filter("DispatchFilter", DispatchFilter.class)
        ).addFilterUrlMapping("DispatchFilter", "/*", DispatcherType.REQUEST);

        deploymentInfo.addErrorPage(new ErrorPage("/WEB-INF/templates/error.jsp"));
        deploymentInfo.addWelcomePage("/index.jsp");
        deploymentInfo.addServlet(JspServletBuilder.createServlet("Default Jsp Servlet", "*.jsp"));
        JspServletBuilder.setupDeployment(deploymentInfo, new HashMap<String, JspPropertyGroup>(), new HashMap<String, TagLibraryInfo>(),
                new HackInstanceManager());
        DeploymentManager deploymentManager = Servlets.defaultContainer().addDeployment(deploymentInfo);
        deploymentManager.deploy();
        HttpHandler pathHandler = Handlers.path(Handlers.redirect("/")).addPrefixPath("/", deploymentManager.start());

        undertow = Undertow.builder()
                .addHttpListener(MVC.port(), MVC.hostName())
                .setHandler(pathHandler)
                .build();
    }


    public static UndertowServer get() {
        return new UndertowServer();
    }


    public void startUp() throws Exception {
        if (undertow == null) {
            init();
        }
        undertow.start();
    }
}
