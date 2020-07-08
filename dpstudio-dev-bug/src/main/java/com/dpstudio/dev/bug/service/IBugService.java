package com.dpstudio.dev.bug.service;

import com.dpstudio.dev.bug.model.Bug;
import com.dpstudio.dev.bug.vo.BugOpVO;
import com.dpstudio.dev.bug.vo.BugQueryVO;
import com.dpstudio.dev.core.R;
import net.ymate.platform.core.persistence.IResultSet;

/**
 * @Author: mengxiang.
 * @Date: 2020/7/6.
 * @Time: 下午3:14.
 * @Description:
 */
public interface IBugService {

    /**
     * 添加问题
     *
     * @param bugOpVO
     * @return
     * @throws Exception
     */
    R create(BugOpVO bugOpVO) throws Exception;

    /**
     * 修改问题
     *
     * @param id
     * @param bugOpVO
     * @return
     * @throws Exception
     */
    R update(String id, BugOpVO bugOpVO) throws Exception;

    /**
     * 详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    Bug detail(String id) throws Exception;

    /**
     * 修改状态
     *
     * @param id
     * @param status
     * @return
     * @throws Exception
     */
    R updateStatus(String id, Integer status) throws Exception;

    /**
     * 列表
     *
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
    IResultSet<BugQueryVO> list(String createUser, String handlerUser, String title, Integer status, Integer type, Integer level, int page, int pageSize) throws Exception;
}
