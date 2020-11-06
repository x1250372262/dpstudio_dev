package com.dpstudio.dev.log;


import com.dpstudio.dev.log.annotation.LogGroup;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/5/26.
 * @Time: 8:49 上午.
 * @Description:
 */
public interface ILogHandler {

    void create(LogGroup logGroup, LR lr) throws Exception;
}
