package com.mx.dev.bean;

import net.ymate.platform.core.persistence.Page;

/**
 * @Author: mengxiang.
 * @Date: 2020/12/14.
 * @Time: 12:02 下午.
 * @Description:
 */
public class PageBean {

    private Integer page;

    private Integer pageSize;

    public PageBean() {
    }

    public PageBean(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Page toPage() {
        return Page.createIfNeed(page, pageSize);
    }

    public static PageBean get(Integer page, Integer pageSize) {
        return new PageBean(page, pageSize);
    }

    public static PageBean noPage() {
        return new PageBean(0, 0);
    }
}
