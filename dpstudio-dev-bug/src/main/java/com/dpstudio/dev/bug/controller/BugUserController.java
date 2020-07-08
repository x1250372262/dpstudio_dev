package com.dpstudio.dev.bug.controller;

import com.dpstudio.dev.bug.model.BugUser;
import com.dpstudio.dev.bug.service.IBugUserService;
import com.dpstudio.dev.core.R;
import com.dpstudio.dev.core.V;
import net.ymate.platform.core.beans.annotation.Inject;
import net.ymate.platform.core.persistence.IResultSet;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;

/**
 * @Author: mengxiang.
 * @Date: 2020/4/27.
 * @Time: 10:42 上午.
 * @Description:
 */
@Controller
@RequestMapping("/dpstudio/bug/user/")
public class BugUserController {

    @Inject
    private IBugUserService iBugUserService;

    /**
     * 登录
     *
     * @param userName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login/", method = Type.HttpMethod.POST)
    public IView login(@VRequired(msg = "请输入名称")
                       @RequestParam String userName) throws Exception {

        R r = iBugUserService.login(userName);
        return V.view(r);
    }

    /**
     * 用户下拉选
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/select")
    public IView select() throws Exception {
        IResultSet<BugUser> resultSet = iBugUserService.select();
        return WebResult.succeed().data(resultSet.getResultData()).toJsonView();
    }
}
