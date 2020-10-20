package com.dpstudio.dev.model;

import com.dpstudio.dev.core.R;
import net.ymate.platform.core.beans.intercept.IInterceptor;
import net.ymate.platform.core.beans.intercept.InterceptContext;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.webmvc.context.WebContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class ModelInterceptor implements IInterceptor {


    @Override
    public Object intercept(InterceptContext context) throws Exception {
        // 判断当前拦截器执行方向
        if (Direction.BEFORE.equals(context.getDirection())) {
            HttpServletRequest request = WebContext.getRequest();
            String requestUrl = request.getRequestURI();
            String scope = context.getContextParams().getOrDefault("SCOPE",Model.REQUEST);
            String filterUrl = context.getContextParams().getOrDefault("URL","");
            String className = context.getContextParams().getOrDefault("CLASS_NAME","");
            if(StringUtils.isBlank(className)){
                throw new NullPointerException("CLASS_NAME");
            }
            IModelHandler iModelHandler = ClassUtils.impl(className,IModelHandler.class,this.getClass());
            if(iModelHandler == null){
                throw new NullPointerException("IModelHandler");
            }
            R result = iModelHandler.result(requestUrl,filterUrl,request);
            if(result.check()){
                Model.get(Model.Type.valueOf(scope)).attrs(result.attrs()).ok();
            }
        }
        return null;
    }

}
