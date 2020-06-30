package com.dpstudio.dev.support.log;

import com.dpstudio.dev.core.R;
import com.dpstudio.dev.support.log.annotation.Log;
import com.dpstudio.dev.support.log.annotation.LogGroup;
import com.dpstudio.dev.support.log.impl.DefaultLogHandler;
import com.dpstudio.dev.spi.SpiLoader;
import net.ymate.platform.core.beans.annotation.Proxy;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.beans.proxy.IProxyChain;
import net.ymate.platform.log.Logs;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/5/26.
 * @Time: 8:40 上午.
 * @Description:
 */
@Proxy(annotation = Log.class)
public class LogProxy implements IProxy {
    @Override
    public Object doProxy(IProxyChain proxyChain) throws Throwable {
        R returnValue = (R) proxyChain.doProxyChain();
        if (!returnValue.logMap().isEmpty()) {
            LogGroup logGroup = proxyChain.getTargetMethod().getAnnotation(LogGroup.class);
            if (logGroup != null) {
                ILogHandler logHandler = SpiLoader.load(ILogHandler.class, logGroup.className());
                if (logHandler == null) {
                    logHandler = new DefaultLogHandler();
                }
                logHandler.create(logGroup, returnValue.logMap());
            }
        } else {
            Logs.get().getLogger().warn("有log注解，但是没有设置对应的logId，请检查代码");
        }
        return returnValue;
    }
}
