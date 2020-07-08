package com.dpstudio.dev.bug.dao.impl;

import com.dpstudio.dev.bug.dao.IBugLogDao;
import com.dpstudio.dev.bug.model.BugLog;
import net.ymate.platform.core.beans.annotation.Bean;
import net.ymate.platform.core.persistence.IResultSet;
import net.ymate.platform.core.persistence.Page;
import net.ymate.platform.persistence.jdbc.query.Cond;
import net.ymate.platform.persistence.jdbc.query.Where;

/**
 * @Author: mengxiang.
 * @Date: 2020/7/6.
 * @Time: 下午3:10.
 * @Description:
 */
@Bean
public class BugLogDaoImpl implements IBugLogDao {

    @Override
    public BugLog create(BugLog bugLog) throws Exception {
        return bugLog.save();
    }

    @Override
    public IResultSet<BugLog> list(String bugId, String handlerUser, int page, int pageSize) throws Exception {
        Cond cond = Cond.create().eq(BugLog.FIELDS.BUG_ID).param(bugId);
        cond.exprNotEmpty(handlerUser, (c) -> {
            c.and().like(BugLog.FIELDS.HANDLER_USER).param("%" + handlerUser + "%");
        });
        return BugLog.builder().build().find(Where.create(cond).orderByDesc(BugLog.FIELDS.HANDLER_TIME), Page.create(page).pageSize(pageSize));
    }

}

