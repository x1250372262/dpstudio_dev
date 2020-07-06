package com.dpstudio.dev.bug.service;

import com.dpstudio.dev.bug.model.BugLog;
import net.ymate.platform.core.persistence.IResultSet;

/**
 * @Author: xujianpeng.
 * @Date: 2020/7/6.
 * @Time: 下午4:09.
 * @Description:
 */
public interface IBugLogService {

    /**
     * 日志列表
     * @param bugId
     * @param handlerUser
     * @param page
     * @param pageSize
     * @return
     * @throws Exception
     */
    IResultSet<BugLog> list(String bugId, String handlerUser, int page, int pageSize) throws Exception;
}
