package com.dpstudio.dev.core;

import net.ymate.platform.webmvc.context.WebContext;
import org.apache.commons.lang.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/6/4.
 * @Time: 1:31 下午.
 * @Description:
 */
public class Model {

    private static boolean __session;

    private Model() {

    }

    private Map<String, Object> attrs = new HashMap<String, Object>();

    public static Model get() {
        return new Model();
    }

    public static Model get(boolean session) {
        __session = session;
        return new Model();
    }

    public Map<String, Object> attrs() {
        return attrs;
    }

    public Model attr(String attrKey, Object attrValue) {
        this.attrs.put(attrKey, attrValue);
        return this;
    }

    public Model attr(String attrKey, Object attrValue, Object defaultValue) {
        this.attrs.put(attrKey, ObjectUtils.defaultIfNull(attrValue, defaultValue));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T attr(String attrKey) {
        return (T) this.attrs.get(attrKey);
    }

    public void ok() {
        if (__session) {
            HttpSession session = WebContext.getRequest().getSession();
            if (session != null) {
                attrs.forEach(session::setAttribute);
            }
        } else {
            HttpServletRequest request = WebContext.getRequest();
            attrs.forEach(request::setAttribute);
        }
    }
}