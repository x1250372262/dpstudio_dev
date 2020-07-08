package com.dpstudio.dev.bug.dao;

import com.dpstudio.dev.bug.model.Bug;
import com.dpstudio.dev.bug.vo.BugQueryVO;
import net.ymate.platform.core.persistence.IResultSet;
import net.ymate.platform.webmvc.annotation.RequestParam;

/**
 * @Author: mengxiang.
 * @Date: 2020/7/6.
 * @Time: 下午3:10.
 * @Description:
 */
public interface IBugDao {

    /**
     * 添加问题
     *
     * @param bug
     * @return
     * @throws Exception
     */
    Bug create(Bug bug) throws Exception;

    /**
     * 修改问题
     *
     * @param bug
     * @param fields
     * @return
     * @throws Exception
     */
    Bug update(Bug bug, String... fields) throws Exception;

    /**
     * 根据id查询
     *
     * @param id
     * @param fields
     * @return
     * @throws Exception
     */
    Bug findById(String id, String... fields) throws Exception;

    /**
     * 查询列表
     * @param createUser
     * @param handlerUser
     * @param title
     * @param status
     * @param type
     * @param level
     * @param page
     * @param pageSize
     * @return
     * @throws Exception
     */
    IResultSet<BugQueryVO> findAll(String createUser, String handlerUser, String title, Integer status, Integer type, Integer level, int page, int pageSize) throws Exception;
}
