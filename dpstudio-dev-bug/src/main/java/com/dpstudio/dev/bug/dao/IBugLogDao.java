package com.dpstudio.dev.bug.dao;

import com.dpstudio.dev.bug.model.BugLog;
import net.ymate.platform.core.persistence.IResultSet;

/**
 * @Author: xujianpeng.
 * @Date: 2020/7/6.
 * @Time: 下午3:10.
 * @Description:
 */
public interface IBugLogDao {

    /**
     * 添加问题记录
     *
     * @param bugLog
     * @return
     * @throws Exception
     */
    BugLog create(BugLog bugLog) throws Exception;


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
