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

    void create(LogGroup logGroup, Map<String, String> logMap) throws Exception;
}
