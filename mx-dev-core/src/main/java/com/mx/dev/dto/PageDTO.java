package com.mx.dev.dto;

import com.mx.dev.bean.PageBean;
import com.mx.dev.utils.BeanUtils;
import net.ymate.platform.validation.validate.VNumeric;
import net.ymate.platform.webmvc.annotation.RequestParam;

/**
 * @Author: mengxiang.
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

    public PageDTO() {
    }

    public PageDTO(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public PageBean toBean() {
        return BeanUtils.copy(this, PageBean::new);
    }

    public static PageDTO noPage(){
        return new PageDTO(0,0);
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
}
