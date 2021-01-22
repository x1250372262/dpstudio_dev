package com.dpstudio.dev.support.log;

import com.dpstudio.dev.core.R;
import com.dpstudio.dev.support.log.annotation.Log;
import com.dpstudio.dev.support.log.annotation.LogGroup;
import com.dpstudio.dev.support.log.exception.LogException;
import com.dpstudio.dev.support.log.impl.DefaultLogHandler;
import com.dpstudio.dev.support.spi.SpiLoader;
import net.ymate.platform.core.beans.annotation.Proxy;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.beans.proxy.IProxyChain;

/**
 * @Author: mengxiang.
 * @Date: 2020/5/26.
 * @Time: 8:40 上午.
 * @Description:
 */
@Proxy(annotation = Log.class)
public class LogProxy implements IProxy {
    @Override
    public Object doProxy(IProxyChain proxyChain) throws Throwable {
        Object returnValue = proxyChain.doProxyChain();
        if (returnValue instanceof R) {
            R result = (R) returnValue;
            if (result.check()) {
                LR lr = result.lr();
                if (lr == null) {
                    throw new LogException("日志功能不完善，请检查代码");
                }
                LogGroup logGroup = proxyChain.getTargetMethod().getAnnotation(LogGroup.class);
                if (logGroup != null) {
                    ILogHandler logHandler = SpiLoader.load(ILogHandler.class, logGroup.className());
                    if (logHandler == null) {
                        logHandler = new DefaultLogHandler();
                    }
                    logHandler.create(logGroup, lr);
                }
            }
            return result;
        }
        return returnValue;
    }
}
