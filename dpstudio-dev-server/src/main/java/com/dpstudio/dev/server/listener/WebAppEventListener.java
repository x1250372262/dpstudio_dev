package com.dpstudio.dev.server.listener;

import net.ymate.platform.commons.util.RuntimeUtils;
import net.ymate.platform.core.IApplication;
import net.ymate.platform.core.IApplicationInitializer;
import net.ymate.platform.core.YMP;
import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.WebEvent;
import net.ymate.platform.webmvc.WebMVC;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * WebMVC框架初始化及上下文事件监听器(初始化YMP框架)
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-7 下午8:33:43
 */
public class WebAppEventListener implements ServletContextListener, ServletContextAttributeListener,
        HttpSessionListener, HttpSessionAttributeListener,
        ServletRequestListener, ServletRequestAttributeListener {

    private static final Log LOG = LogFactory.getLog(WebAppEventListener.class);

    private IApplication application;

    private IWebMvc owner;

    private boolean initialized;

    private IApplicationInitializer iApplicationInitializer;

    public WebAppEventListener(){

    }

    public WebAppEventListener(IApplicationInitializer iApplicationInitializer){
        this.iApplicationInitializer = iApplicationInitializer;
    }

    private void doFireEvent(WebEvent.EVENT event, Object eventSource) {
        if (initialized) {
            application.getEvents().fireEvent(new WebEvent(owner, event).addParamExtend(WebEvent.EVENT_SOURCE, eventSource));
        }
    }

    //// ServletContextListener

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            if(iApplicationInitializer != null){
                application = YMP.run(iApplicationInitializer);
            }else{
                application = YMP.run();
            }
            if (application != null && application.isInitialized()) {
                owner = application.getModuleManager().getModule(WebMVC.class);
                initialized = owner != null && owner.isInitialized();
            }
            doFireEvent(WebEvent.EVENT.SERVLET_CONTEXT_INITIALIZED, sce);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(StringUtils.EMPTY, RuntimeUtils.unwrapThrow(e));
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        doFireEvent(WebEvent.EVENT.SERVLET_CONTEXT_DESTROYED, sce);
        if (initialized) {
            try {
                application.close();
            } catch (Exception e) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn(StringUtils.EMPTY, RuntimeUtils.unwrapThrow(e));
                }
            }
        }
    }

    //// ServletContextAttributeListener

    @Override
    public void attributeAdded(ServletContextAttributeEvent scab) {
        doFireEvent(WebEvent.EVENT.SERVLET_CONTEXT_ATTR_ADDED, scab);
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scab) {
        doFireEvent(WebEvent.EVENT.SERVLET_CONTEXT_ATTR_REMOVED, scab);
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scab) {
        doFireEvent(WebEvent.EVENT.SERVLET_CONTEXT_ATTR_REPLACED, scab);
    }

    //// HttpSessionListener

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        doFireEvent(WebEvent.EVENT.SESSION_CREATED, se);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        doFireEvent(WebEvent.EVENT.SESSION_DESTROYED, se);
    }

    //// HttpSessionAttributeListener

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        doFireEvent(WebEvent.EVENT.SESSION_ATTR_ADDED, se);
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        doFireEvent(WebEvent.EVENT.SESSION_ATTR_REMOVED, se);
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        doFireEvent(WebEvent.EVENT.SESSION_ATTR_REPLACED, se);
    }

    //// ServletRequestListener

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        doFireEvent(WebEvent.EVENT.REQUEST_INITIALIZED, sre);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        doFireEvent(WebEvent.EVENT.REQUEST_DESTROYED, sre);
    }

    //// ServletRequestAttributeListener

    @Override
    public void attributeAdded(ServletRequestAttributeEvent srae) {
        doFireEvent(WebEvent.EVENT.REQUEST_ATTR_ADDED, srae);
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent srae) {
        doFireEvent(WebEvent.EVENT.REQUEST_ATTR_REMOVED, srae);
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent srae) {
        doFireEvent(WebEvent.EVENT.REQUEST_ATTR_REPLACED, srae);
    }
}
