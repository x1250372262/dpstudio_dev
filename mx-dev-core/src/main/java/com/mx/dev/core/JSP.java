package com.dpstudio.dev.core;

import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mengxiang.
 * @Date: 2019-04-23.
 * @Time: 17:49.¬
 * @Description: jsp返回结果
 */
public class JSP implements Serializable {

    private Map<String, Object> attrs = new HashMap<String, Object>();


    private JSP() {
    }

    public IView view(String path) {
        return View.jspView(path).addAttributes(attrs);
    }

    public static JSP create() {
        return new JSP();
    }

    public Map<String, Object> attrs() {
        return attrs;
    }

    @SuppressWarnings("unchecked")
    public <T> T attr(String attrKey) {
        return (T) this.attrs.get(attrKey);
    }

    public JSP attr(String attrKey, Object attrValue) {
        this.attrs.put(attrKey, attrValue);
        return this;
    }

    public JSP attr(String attrKey, Object attrValue, Object defaultValue) {
        this.attrs.put(attrKey, ObjectUtils.defaultIfNull(attrValue, defaultValue));
        return this;
    }

    public JSP attrs(Map<String, Object> attrs) {
        this.attrs = attrs;
        return this;
    }


}
