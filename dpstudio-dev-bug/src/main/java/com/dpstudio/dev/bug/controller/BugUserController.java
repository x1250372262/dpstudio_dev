package com.dpstudio.dev.bug.controller;

import com.dpstudio.dev.bug.model.BugUser;
import com.dpstudio.dev.core.CommonResult;
import com.dpstudio.dev.core.code.CommonCode;
import net.ymate.framework.webmvc.support.UserSessionBean;
import net.ymate.platform.persistence.IResultSet;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
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
@RequestMapping("/dpstudio/bug/user/")
public class BugUserController {

    @RequestMapping(value = "/login/", method = Type.HttpMethod.POST)
    public IView login(@VRequired(msg = "请输入名称")
                       @RequestParam String userName) throws Exception {

        BugUser bugUser = BugUser.builder().name(userName).build().findFirst();
        if (bugUser == null) {
            return WebResult.create(CommonCode.COMMON_OPTION_ERROR.getCode()).msg("请手动在数据库添加用户").toJSON();
        }
        UserSessionBean.create().setUid(bugUser.getId()).addAttribute("name", userName).save();
        return CommonResult.successView();
    }

    @RequestMapping("/select")
    public IView select() throws Exception {
        IResultSet<BugUser> resultSet = BugUser.builder().build().find();
        return WebResult.succeed().data(resultSet.getResultData()).toJSON();
    }
}
