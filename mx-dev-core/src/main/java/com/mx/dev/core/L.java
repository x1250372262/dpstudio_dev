package com.mx.dev.core;

import net.ymate.platform.core.persistence.IResultSet;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;
import org.apache.commons.lang.NullArgumentException;

import java.util.List;


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
        if(resultSet == null){
            throw new NullArgumentException("resultSet");
        }
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
        if(resultSet == null){
            throw new NullArgumentException("resultSet");
        }
        return WebResult.succeed().data(resultSet.getResultData()).keepNullValue().toJsonView();
    }

    /**
     * 查询列表
     */
    public IView listView(List<T> resultSet) {
        if(resultSet == null){
            throw new NullArgumentException("resultSet");
        }
        return WebResult.succeed().data(resultSet).keepNullValue().toJsonView();
    }

    /**
     * 详情
     *
     * @param t
     * @return
     * @throws Exception
     */
    public IView detailView(T t) {
        if(t == null){
            throw new NullArgumentException("t");
        }
        return WebResult.succeed().data(t).keepNullValue().toJsonView();
    }
}
