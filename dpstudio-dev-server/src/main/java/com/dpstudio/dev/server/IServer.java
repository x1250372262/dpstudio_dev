package com.dpstudio.dev.server;

import net.ymate.platform.core.IApplication;
import net.ymate.platform.core.beans.annotation.Ignored;
import net.ymate.platform.core.support.IDestroyable;
import net.ymate.platform.core.support.IInitialization;

/**
 * @Author: mengxiang.
 * @Date: 2020/3/17.
 * @Time: 5:27 下午.
 * @Description:
 */
@Ignored
public interface IServer extends IInitialization<IApplication>, IDestroyable {

    void startUp();

    String MODULE_NAME = "dpstudio.server";

    String CHARSET = "utf-8";

    /**
     * 获取所属应用容器
     *
     * @return 返回所属应用容器实例
     */
    IApplication getOwner();

    /**
     * 获取配置
     *
     * @return 返回配置对象
     */
    IServerConfig getConfig();
}
