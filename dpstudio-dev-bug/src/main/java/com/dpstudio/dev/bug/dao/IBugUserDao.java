package com.dpstudio.dev.bug.dao;

import com.dpstudio.dev.bug.model.BugUser;
import net.ymate.platform.core.persistence.IResultSet;

/**
 * @Author: mengxiang.
 * @Date: 2020/7/6.
 * @Time: 下午4:04.
 * @Description:
 */
public interface IBugUserDao {

    /**
     * 根据名称查询
     * @param name
     * @return
     * @throws Exception
     */
    BugUser findByName(String name) throws Exception;

    /**
     * 查询所有
     * @return
     * @throws Exception
     */
    IResultSet<BugUser> findAll() throws Exception;
}
