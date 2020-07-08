package com.dpstudio.dev.bug.interceptor;

import net.ymate.platform.core.beans.annotation.Before;
import net.ymate.platform.core.beans.annotation.Clean;
import net.ymate.platform.webmvc.IInterceptorRule;
import net.ymate.platform.webmvc.annotation.InterceptorRule;

/**
 * Author: mengxiang.
 * Date: 17/5/2.
 * Time: 09:16.
 * 后台拦截session¬
 */
@InterceptorRule
@Before(SessionCheckInterceptor.class)
public class BugInterCeptor implements IInterceptorRule {
    @InterceptorRule("/dpstudio/bug/*")
    public void adminAll() {
    }

    @Clean
    @InterceptorRule("/dpstudio/bug/login_view")
    public void adminLogin() {
    }
}