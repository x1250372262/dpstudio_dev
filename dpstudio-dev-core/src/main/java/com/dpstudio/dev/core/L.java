package com.dpstudio.dev.core;

import net.ymate.platform.core.persistence.IResultSet;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;


/**
 * @Author: mengxiang.
 * @Date: 2019-04-23.
 * @Time: 17:49.¬
 * @Description: 通用返回结果
 */
public class L<T> {


    /**
     * 查询列表
     */
    public IView listView(IResultSet<T> resultSet, Integer page) {
        assert resultSet != null;
        return WebResult.succeed().data(resultSet.getResultData())
                .attr("total", resultSet.getRecordCount())
                .attr("pageCount", resultSet.getPageCount())
                .attr("pageNumber", resultSet.getPageNumber())
                .attr("page", page).keepNullValue().toJsonView();
    }

    /**
     * 查询列表
     */
    public IView listView(IResultSet<T> resultSet) {
        assert resultSet != null;
        return WebResult.succeed().data(resultSet.getResultData()).keepNullValue().toJsonView();
    }

    /**
     * 详情
     *
     * @param t
     * @return
     * @throws Exception
     */
    public IView detailView(T t) {
        assert t != null;
        return WebResult.succeed().data(t).keepNullValue().toJsonView();
    }
}
