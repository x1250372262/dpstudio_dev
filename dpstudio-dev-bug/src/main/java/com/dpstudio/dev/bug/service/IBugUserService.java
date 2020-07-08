package com.dpstudio.dev.bug.service;

import com.dpstudio.dev.bug.model.BugUser;
import com.dpstudio.dev.core.R;
import net.ymate.platform.core.persistence.IResultSet;

/**
 * @Author: mengxiang.
 * @Date: 2020/7/6.
 * @Time: 下午4:03.
 * @Description:
 */
public interface IBugUserService {

    /**
     * 登录
     * @param userName
     * @return
     * @throws Exception
     */
    R login(String userName) throws Exception;

    /**
     * 下拉选
     * @return
     * @throws Exception
     */
    IResultSet<BugUser> select() throws Exception;
}
