package com.dpstudio.dev.bug.dao.impl;

import com.dpstudio.dev.bug.dao.IBugUserDao;
import com.dpstudio.dev.bug.model.BugUser;
import net.ymate.platform.core.beans.annotation.Bean;
import net.ymate.platform.core.persistence.IResultSet;

/**
 * @Author: xujianpeng.
 * @Date: 2020/7/6.
 * @Time: 下午4:05.
 * @Description:
 */
@Bean
public class BugUserDaoImpl implements IBugUserDao {
    @Override
    public BugUser findByName(String name) throws Exception {
        return BugUser.builder().name(name).build().findFirst();
    }

    @Override
    public IResultSet<BugUser> findAll() throws Exception {
        return BugUser.builder().build().find();
    }
}
