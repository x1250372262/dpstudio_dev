package com.mx.dev.dto;

import net.ymate.platform.webmvc.annotation.RequestParam;

/**
 * @Author: mengxiang.
 * @Date: 2020/12/14.
 * @Time: 12:02 下午.
 * @Description:
 */
public class ExportDTO {

    @RequestParam(defaultValue = "0")
    private Integer export;

    @RequestParam(value = "ids[]")
    private String[] ids;

    public Integer getExport() {
        return export;
    }

    public void setExport(Integer export) {
        this.export = export;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
