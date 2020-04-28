package com.dpstudio.dev.security.taglib;

import com.dpstudio.dev.security.IAuthenticator;
import com.dpstudio.dev.security.Security;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.List;

public class PermissionTag extends BodyTagSupport {

    private String permission;

    private Boolean checkPermission(String permission) {

        IAuthenticator iAuthenticator = Security.get().getModuleCfg().getAuthenticator();

        if (iAuthenticator == null) {
            throw new NullArgumentException("authenticator_class");
        }

        List<String> userPermissionCodes = null;
        try {
            //是总管理
            if (iAuthenticator.isFounder()) {
                return true;
            }
            userPermissionCodes = iAuthenticator.userPermission();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userPermissionCodes == null || userPermissionCodes.size() <= 0) {
            return false;
        }
        if (StringUtils.isBlank(permission)) {
            return false;
        }

        String[] permissions = StringUtils.split(StringUtils.trimToEmpty(permission), "|");

        for (String str : userPermissionCodes) {
            if (!ArrayUtils.contains(permissions, str)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public int doStartTag() {
        // 如果为true则显示文本中的内容，否则不显示
        if (checkPermission(permission)) {
            return EVAL_BODY_INCLUDE;
        } else {
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
