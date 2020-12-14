package com.dpstudio.dev.dto;

import net.ymate.platform.core.persistence.Page;
import net.ymate.platform.validation.validate.VNumeric;
import net.ymate.platform.webmvc.annotation.RequestParam;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/12/14.
 * @Time: 12:02 下午.
 * @Description:
 */
public class PageDTO {

    @VNumeric
    @RequestParam(defaultValue = "1")
    private Integer page;

    @VNumeric
    @RequestParam(defaultValue = "10")
    private Integer pageSize;


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

    public Page toPage(){
        return Page.createIfNeed(page,pageSize);
    }
    public static PageDTO get(){
        return new PageDTO();
    }
}
