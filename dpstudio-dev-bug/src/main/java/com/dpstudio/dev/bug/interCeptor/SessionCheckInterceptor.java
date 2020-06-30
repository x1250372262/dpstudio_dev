package com.dpstudio.dev.bug.interCeptor;

//import net.ymate.framework.webmvc.support.UserSessionBean;
import net.ymate.platform.core.beans.intercept.IInterceptor;
import net.ymate.platform.core.beans.intercept.InterceptContext;
import net.ymate.platform.core.beans.intercept.InterceptException;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.context.WebContext;
//import net.ymate.platform.webmvc.util.ErrorCode;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.util.WebUtils;
import net.ymate.platform.webmvc.view.View;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/27.
 * @Time: 11:17 上午.
 * @Description:
 */
public class SessionCheckInterceptor implements IInterceptor {

    @Override
    public Object intercept(InterceptContext context) throws InterceptException {
        // 判断当前拦截器执行方向
//        if (Direction.BEFORE.equals(context.getDirection())) {
//            UserSessionBean _sessionBean = UserSessionBean.current(context);
//            if (_sessionBean == null) {
//                ErrorCode _message = ErrorCode.create(ErrorCode.USER_SESSION_INVALID_OR_TIMEOUT, "用户未授权登录或会话已过期，请重新登录");
//                //
//                if (WebUtils.isAjax(WebContext.getRequest(), true, true)) {
//                    return WebResult.formatView(WebResult.create(WebContext.getContext().getOwner(), _message), Type.Const.FORMAT_JSON);
//                }
//                return View.jspView("dpstudio/bug/login_view");
//            } else {
//                // 更新会话最后活动时间
//                _sessionBean.touch();
//            }
//        }
        return null;
    }
}
