package com.dpstudio.dev.bug.service.impl;

import com.dpstudio.dev.bug.dao.IBugLogDao;
import com.dpstudio.dev.bug.model.BugLog;
import com.dpstudio.dev.bug.service.IBugLogService;
import net.ymate.platform.core.beans.annotation.Bean;
import net.ymate.platform.core.beans.annotation.Inject;
import net.ymate.platform.core.persistence.IResultSet;

/**
 * @Author: xujianpeng.
 * @Date: 2020/7/6.
 * @Time: 下午4:09.
 * @Description:
 */
@Bean
public class BugLogServiceImpl implements IBugLogService {

    @Inject
    private IBugLogDao iBugLogDao;

    @Override
    public IResultSet<BugLog> list(String bugId, String handlerUser, int page, int pageSize) throws Exception {
        return iBugLogDao.list(bugId, handlerUser, page, pageSize);
    }
}
