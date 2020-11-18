package com.dpstudio.dev.security.taglib;

import com.dpstudio.dev.security.IAuthenticator;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.List;

public class PermissionTag extends BodyTagSupport {

    private String permission;

    private Boolean checkPermission(String code) throws Exception {

        //如果权限是空的 证明不需要
        if (StringUtils.isBlank(code)) {
            return true;
        }

        IAuthenticator iAuthenticator = com.dpstudio.dev.security.Security.get().getModuleCfg().getAuthenticator();

        if (iAuthenticator == null) {
            throw new NullArgumentException("authenticator_class");
        }
        //是总管理
        if (iAuthenticator.isFounder()) {
            return true;
        }
        List<String> userPermissionCodes = iAuthenticator.userPermission();

        //这个人没有权限不行
        if (userPermissionCodes == null || userPermissionCodes.isEmpty()) {
            return false;
        }
        //判断是否包含权限
        return userPermissionCodes.contains(permission);
    }


    @Override
    public int doStartTag() {
        // 如果为true则显示文本中的内容，否则不显示
        try {
            if (checkPermission(permission)) {
                return EVAL_BODY_INCLUDE;
            } else {
                return SKIP_BODY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SKIP_BODY;
        }

    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
