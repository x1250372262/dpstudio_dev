package com.dpstudio.dev.bug.controller;

import com.dpstudio.dev.bug.interceptor.SessionCheckInterceptor;
import com.dpstudio.dev.bug.model.BugLog;
import com.dpstudio.dev.bug.service.IBugLogService;
import com.dpstudio.dev.core.L;
import net.ymate.platform.core.beans.annotation.Before;
import net.ymate.platform.core.beans.annotation.Inject;
import net.ymate.platform.core.persistence.IResultSet;
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

    @Inject
    private IBugLogService iBugLogService;

    @RequestMapping(value = "/list/")
    public IView list(@VRequired
                       @RequestParam String bugId,
                       @RequestParam String handlerUser,
                       @RequestParam(defaultValue = "1") final int page,
                       @RequestParam(defaultValue = "10") final int pageSize) throws Exception {

        IResultSet<BugLog> resultSet = iBugLogService.list(bugId, handlerUser, page, pageSize);
        return new L<BugLog>().listView(resultSet, page);
    }
}
