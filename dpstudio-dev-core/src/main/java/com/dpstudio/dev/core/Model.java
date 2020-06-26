package com.dpstudio.dev.core;

import net.ymate.platform.webmvc.context.WebContext;
import org.apache.commons.lang.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private Model() {

    }

    private static Type __type;

    public enum Type {
        SESSION,
        REQUEST,
        RESPONSE
    }

    private Map<String, Object> attrs = new HashMap<String, Object>();

    public static Model get() {
        __type = Type.REQUEST;
        return new Model();
    }

    public static Model get(Type type) {
        __type = type;
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
        switch (__type) {
            case REQUEST:
                request();
                break;
            case SESSION:
                session();
                break;
            case RESPONSE:
                response();
                break;
            default:
                request();
                break;

        }
    }

    private void request() {
        HttpServletRequest request = WebContext.getRequest();
        if (request != null) {
            attrs.forEach(request::setAttribute);
        }
    }

    private void session() {
        HttpSession session = WebContext.getRequest().getSession();
        if (session != null) {
            attrs.forEach(session::setAttribute);
        }
    }

    private void response() {
        HttpServletResponse response = WebContext.getResponse();
        if (response != null) {
            attrs.forEach((k, v) -> {
                response.setHeader(k, v.toString());
            });
        }
    }
}