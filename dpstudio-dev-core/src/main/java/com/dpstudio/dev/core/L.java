package com.dpstudio.dev.core;

import net.ymate.platform.core.persistence.IResultSet;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;


/**
 * @Author: 徐建鹏.
 * @Date: 2019-04-23.
 * @Time: 17:49.¬
 * @Description: 通用返回结果
 */
public class L<T> {


    /**
     * 查询列表
     */
    public IView listView(IResultSet<T> resultSet, int page) {
        return WebResult.succeed().data(resultSet.getResultData())
                .attr("total", resultSet.getRecordCount())
                .attr("page", page).keepNullValue().toJsonView();
    }

    /**
     * 查询列表
     */
    public IView listView(IResultSet<T> resultSet) {
        return WebResult.succeed().data(resultSet.getResultData()).keepNullValue().toJsonView();
    }
}
