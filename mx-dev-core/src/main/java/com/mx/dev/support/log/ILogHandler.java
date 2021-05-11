package com.mx.dev.support.log;


import com.mx.dev.support.log.annotation.LogGroup;

/**
 * @Author: mengxiang.
 * @Date: 2020/5/26.
 * @Time: 8:49 上午.
 * @Description:
 */
public interface ILogHandler {

    void create(LogGroup logGroup, LR lr) throws Exception;
}
