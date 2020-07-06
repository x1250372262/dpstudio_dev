package com.dpstudio.dev.bug.controller;

import com.dpstudio.dev.bug.interceptor.SessionCheckInterceptor;
import com.dpstudio.dev.bug.model.Bug;
import com.dpstudio.dev.bug.service.IBugService;
import com.dpstudio.dev.bug.vo.BugOpVO;
import com.dpstudio.dev.bug.vo.BugQueryVO;
import com.dpstudio.dev.core.L;
import com.dpstudio.dev.core.R;
import com.dpstudio.dev.core.V;
import net.ymate.platform.core.beans.annotation.Before;
import net.ymate.platform.core.beans.annotation.Inject;
import net.ymate.platform.core.persistence.IResultSet;
import net.ymate.platform.core.persistence.annotation.Transaction;
import net.ymate.platform.validation.annotation.VModel;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.ModelBind;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/27.
 * @Time: 10:42 上午.
 * @Description:
 */
@Controller
@RequestMapping("/dpstudio/bug/")
@Before(SessionCheckInterceptor.class)
public class BugController {

    @Inject
    private IBugService iBugService;

    @RequestMapping(value = "/list/")
    public IView login(@RequestParam String createUser,
                       @RequestParam String handlerUser,
                       @RequestParam String title,
                       @RequestParam Integer status,
                       @RequestParam Integer type,
                       @RequestParam Integer level,
                       @RequestParam(defaultValue = "1") final int page,
                       @RequestParam(defaultValue = "10") final int pageSize) throws Exception {

        IResultSet<BugQueryVO> resultSet = iBugService.list(createUser, handlerUser, title, status, type, level, page, pageSize);
        return new L<BugQueryVO>().listView(resultSet, page);
    }


    @Transaction
    @RequestMapping(value = "/create/", method = Type.HttpMethod.POST)
    public IView login(@VModel
                       @ModelBind BugOpVO bugOpVO) throws Exception {
        R r = iBugService.create(bugOpVO);
        return V.view(r);
    }


    @Transaction
    @RequestMapping(value = "/update/", method = Type.HttpMethod.POST)
    public IView login(@VRequired(msg = "id不能为空")
                       @RequestParam String id,
                       @VModel
                       @ModelBind BugOpVO bugOpVO) throws Exception {

        R r = iBugService.update(id, bugOpVO);
        return V.view(r);
    }


    @RequestMapping(value = "/detail/", method = Type.HttpMethod.GET)
    public IView login(@VRequired(msg = "id不能为空")
                       @RequestParam String id) throws Exception {

        Bug bug = iBugService.detail(id);
        return WebResult.succeed().data(bug).toJsonView();
    }


    @Transaction
    @RequestMapping(value = "/status/", method = Type.HttpMethod.POST)
    public IView login(@VRequired(msg = "id不能为空")
                       @RequestParam String id,
                       @VRequired(msg = "status不能为空")
                       @RequestParam Integer status) throws Exception {

        R r = iBugService.updateStatus(id, status);
        return V.view(r);
    }
}
