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

    private Boolean checkPermission(String code) throws Exception {

        IAuthenticator iAuthenticator = com.dpstudio.dev.security.Security.get().getModuleCfg().getAuthenticator();

        if (iAuthenticator == null) {
            throw new NullArgumentException("authenticator_class");
        }
        //是总管理
        if (iAuthenticator.isFounder()) {
            return true;
        }
        List<String> userPermissionCodes = iAuthenticator.userPermission();
        if (userPermissionCodes == null) {
            return false;
        }
        if (!userPermissionCodes.contains(code)) {
            return false;
        }
        return true;
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
        }catch (Exception e){
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
