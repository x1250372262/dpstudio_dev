package com.dpstudio.dev.bug.service.impl;

import com.dpstudio.dev.bug.dao.IBugUserDao;
import com.dpstudio.dev.bug.model.BugUser;
import com.dpstudio.dev.bug.service.IBugUserService;
import com.dpstudio.dev.core.R;
import com.dpstudio.dev.core.UserSession;
import net.ymate.platform.core.beans.annotation.Bean;
import net.ymate.platform.core.beans.annotation.Inject;
import net.ymate.platform.core.persistence.IResultSet;

/**
 * @Author: mengxiang.
 * @Date: 2020/7/6.
 * @Time: 下午4:03.
 * @Description:
 */
@Bean
public class BugUserServiceImpl implements IBugUserService {

    @Inject
    private IBugUserDao iBugUserDao;

    @Override
    public R login(String userName) throws Exception {
        BugUser bugUser = iBugUserDao.findByName(userName);
        if (bugUser == null) {
            return R.fail().msg("请手动在数据库添加用户");
        }
        UserSession.create().setUid(bugUser.getId()).addAttribute("name", userName).save();
        return R.ok();
    }

    @Override
    public IResultSet<BugUser> select() throws Exception {
        return iBugUserDao.findAll();
    }
}
