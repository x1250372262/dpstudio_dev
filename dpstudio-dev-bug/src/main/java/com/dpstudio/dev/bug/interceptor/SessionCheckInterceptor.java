package com.dpstudio.dev.bug.interceptor;


import com.dpstudio.dev.core.UserSession;
import com.dpstudio.dev.core.code.C;
import net.ymate.platform.core.beans.intercept.IInterceptor;
import net.ymate.platform.core.beans.intercept.InterceptContext;
import net.ymate.platform.core.beans.intercept.InterceptException;
import net.ymate.platform.core.support.ErrorCode;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.util.WebUtils;
import net.ymate.platform.webmvc.view.View;


/**
 * @Author: mengxiang.
 * @Date: 2020/4/27.
 * @Time: 11:17 上午.
 * @Description:
 */
public class SessionCheckInterceptor implements IInterceptor {

    @Override
    public Object intercept(InterceptContext context) throws InterceptException {
        // 判断当前拦截器执行方向
        if (Direction.BEFORE.equals(context.getDirection())) {
            UserSession userSession = UserSession.current();
            if (userSession == null) {
                ErrorCode message = ErrorCode.create(C.NOT_LOGIN.getCode(), C.NOT_LOGIN.getMsg());
                if (WebUtils.isAjax(WebContext.getRequest())) {
                    return WebResult.formatView(WebResult.create(WebContext.getContext().getOwner(), message), Type.Const.FORMAT_JSON);
                }
                return View.jspView("dpstudio/bug/login_view");
            } else {
                // 更新会话最后活动时间
                userSession.touch();
            }
        }
        return null;
    }
}
