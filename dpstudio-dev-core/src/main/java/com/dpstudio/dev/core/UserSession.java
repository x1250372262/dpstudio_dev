package com.dpstudio.dev.core;

import net.ymate.platform.commons.util.DateTimeUtils;
import net.ymate.platform.webmvc.context.WebContext;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserSession implements Serializable {

    private final String id;

    private String uid;

    private long createTime;

    private long lastActivateTime;

    private Map<String, Serializable> attributes;

    private static final HttpSession HTTP_SESSION = WebContext.getRequest().getSession();


    private UserSession() {
        this(null);
    }

    private UserSession(String id) {
        this.id = StringUtils.defaultIfBlank(id, HTTP_SESSION.getId());
        if (StringUtils.isBlank(this.id)) {
            throw new NullArgumentException("id");
        }
        this.createTime = DateTimeUtils.currentTimeMillis();
        this.lastActivateTime = this.createTime;
        this.attributes = new HashMap<>();
    }

    /**
     * @return 创建UserSessionBean对象并存入当前会话中
     */
    public static UserSession create() {
        return new UserSession();
    }

    public static UserSession create(String id) {
        return new UserSession(id);
    }

    /**
     * @return 获取当前会话中的UserSessionBean对象, 若不存在将返回null值
     */
    public static UserSession current() {
        return (UserSession) HTTP_SESSION.getAttribute(UserSession.class.getName());
    }

    /**
     * @return 更新会话最后活动时间(毫秒)
     */
    public UserSession touch() {
        this.lastActivateTime = DateTimeUtils.currentTimeMillis();
        return this;
    }

    /**
     * @return 验证当前会话是否合法有效(若IUserSessionHandler配置参数为空则该方法返回值永真)
     */
    public boolean isVerified() {
        return HTTP_SESSION.getAttribute(this.getClass().getName()) != null;
    }

    /**
     * @return 重置(会话ID将保留不变)
     */
    public UserSession reset() {
        this.createTime = System.currentTimeMillis();
        this.lastActivateTime = this.createTime;
        this.attributes = new HashMap<>();
        return this;
    }

    public UserSession save() {
        HTTP_SESSION.setAttribute(this.getClass().getName(), this);
        return this;
    }

    public void destroy() {
        HTTP_SESSION.setAttribute(this.getClass().getName(), this);
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public UserSession setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getLastActivateTime() {
        return lastActivateTime;
    }

    public boolean hasAttribute(String name) {
        return attributes.containsKey(name);
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getAttribute(String name) {
        return (T) attributes.get(name);
    }

    public <T extends Serializable> UserSession addAttribute(String name, T value) {
        attributes.put(name, value);
        return this;
    }

    public Map<String, ? extends Serializable> getAttributes() {
        return attributes;
    }
}
