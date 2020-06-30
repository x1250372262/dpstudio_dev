package com.dpstudio.dev.support.log;


import com.dpstudio.dev.support.log.annotation.LogGroup;

import java.util.Map;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/5/26.
 * @Time: 8:49 上午.
 * @Description:
 */
public interface ILogHandler {

    /**
     * 创建日志
     *
     * @param logGroup 日志分组
     * @param logMap   日志数据
     * @throws Exception 创建异常
     */
    void create(LogGroup logGroup, Map<String, String> logMap) throws Exception;
}
