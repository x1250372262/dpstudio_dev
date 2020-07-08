package com.dpstudio.dev.doc.proxy;

import com.dpstudio.dev.core.R;
import com.dpstudio.dev.core.V;
import com.dpstudio.dev.doc.Doc;
import com.dpstudio.dev.doc.annotation.Mock;
import com.dpstudio.dev.doc.bean.ApiAction;
import com.dpstudio.dev.doc.bean.ApiModule;
import com.dpstudio.dev.doc.bean.ApiResult;
import net.ymate.platform.commons.json.IJsonObjectWrapper;
import net.ymate.platform.commons.json.JsonWrapper;
import net.ymate.platform.core.beans.annotation.Proxy;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.beans.proxy.IProxyChain;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @Author: mengxiang.
 * @Date: 2019-01-17.
 * @Time: 15:38.
 * @Description:
 */
@Proxy(annotation = Mock.class)
public class MockProxy implements IProxy {

    @Override
    public Object doProxy(IProxyChain proxyChain) throws Throwable {
        //模块禁用 或者 mock禁用
        if (!Doc.get().getConfig().isEnabled() || !Doc.get().getConfig().isMockEnabled()) {
            return proxyChain.doProxyChain();
        }
        if (proxyChain.getTargetClass().isAnnotationPresent(Mock.class)) {
            Mock mock = proxyChain.getTargetMethod().getAnnotation(Mock.class);
            if (mock != null && mock.enabled()) {
                String responseBody = mock.value();
                if (StringUtils.isBlank(responseBody)) {
                    ApiResult apiResult = Doc.get().getDoc();
                    String className = proxyChain.getTargetClass().getName();
                    for (ApiModule apiModule : apiResult.getApiModuleList()) {
                        if (Objects.equals(className, apiModule.getType().getName())) {
                            Optional<ApiAction> actionOptional = apiModule.getApiActions().stream().filter(apiAction -> apiAction.getMethod().equals(proxyChain.getTargetMethod())).findFirst();
                            if (actionOptional.isPresent()) {
                                responseBody = actionOptional.get().getRespbody();
                            }
                            break;
                        }
                    }
                }
                IJsonObjectWrapper jsonWrappedObject = JsonWrapper.fromJson(responseBody).getAsJsonObject();
                return V.view(R.create().attrs(jsonWrappedObject.toMap()));
            }
        }
        return proxyChain.doProxyChain();
    }
}
