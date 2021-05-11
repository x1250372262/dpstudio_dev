package com.mx.dev.interCeptor;

import com.mx.dev.support.UserSession;
import com.mx.dev.code.C;
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
 * @Date: 2020/7/25.
 * @Time: 上午11:06.
 * @Description:
 */
public class UserSessionCheckInterceptor implements IInterceptor {

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
                return View.jspView("admin/login_view");
            } else {
                // 更新会话最后活动时间
                userSession.touch();
            }
        }
        return null;
    }
}
