package com.dpstudio.dev.jdbc.session;

import net.ymate.platform.persistence.jdbc.ISession;

import java.util.List;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/5/15.
 * @Time: 9:30 上午.
 * @Description:
 */
public interface IDSession {

    ISession get(String ds) throws Exception;

    ISession get() throws Exception;

    int insertAll(List<?> entities) throws Exception;
}
