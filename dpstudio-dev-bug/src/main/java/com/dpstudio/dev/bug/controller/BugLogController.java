package com.dpstudio.dev.bug.controller;

import com.dpstudio.dev.bug.interCeptor.SessionCheckInterceptor;
import com.dpstudio.dev.bug.model.BugLog;
import com.dpstudio.dev.core.L;
import net.ymate.platform.core.beans.annotation.Before;
import net.ymate.platform.core.persistence.IResultSet;
import net.ymate.platform.core.persistence.Page;
import net.ymate.platform.persistence.jdbc.query.Cond;
import net.ymate.platform.persistence.jdbc.query.Where;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.view.IView;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/27.
 * @Time: 10:42 上午.
 * @Description:
 */
@Controller
@RequestMapping("/dpstudio/bug/log/")
@Before(SessionCheckInterceptor.class)
public class BugLogController {

    @RequestMapping(value = "/list/")
    public IView login(@VRequired
                       @RequestParam String bugId,
                       @RequestParam String handlerUser,
                       @RequestParam(defaultValue = "1") final int page,
                       @RequestParam(defaultValue = "10") final int pageSize) throws Exception {

        final Cond cond = Cond.create().eq(BugLog.FIELDS.BUG_ID).param(bugId);
        cond.exprNotEmpty(handlerUser, (c) -> {
            c.and().like(BugLog.FIELDS.HANDLER_USER).param("%" + handlerUser + "%");
        });
        IResultSet<BugLog> resultSet = BugLog.builder().build().find(Where.create(cond).orderByDesc(BugLog.FIELDS.HANDLER_TIME), Page.create(page).pageSize(pageSize));
        return new L<BugLog>().listView(resultSet, page);
    }
}
