package com.mx.dev.excel.export;

import com.mx.dev.bean.PageBean;
import com.mx.dev.excel.ExcelAliExportHelper;
import com.mx.dev.excel.ExcelExportHelper;
import com.mx.dev.excel.exception.ExcelException;
import com.mx.dev.utils.ListUtils;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.util.WebUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: mengxiang.
 * @create: 2021-03-04 15:48
 * @Description:
 */
public class ExportHelper<T> {

    public interface IExportData<T> {
        List<T> exportDataList(PageBean pageBean) throws Exception;
    }

    public enum Type {
        /**
         * zip
         */
        ZIP,

        /**
         * excel
         */
        EXCEL
    }

    public String exportAli(String title, Class<T> voClass, Type type, PageBean pageBean, String zipFilePath, String excelFilePath, IExportData<T> iExportData) throws Exception {
        ExcelAliExportHelper<T> helper = new ExcelAliExportHelper<T>().init(voClass, excelFilePath, zipFilePath);
        while (true) {
            List<T> resultSet = iExportData.exportDataList(pageBean);
            if (ListUtils.isNotEmpty(resultSet)) {
                helper.addData(resultSet);
                int page = pageBean.getPage();
                page++;
                pageBean.setPage(page);
            } else {
                break;
            }
        }
        File file;
        switch (type) {
            case ZIP:
                file = helper.export(title);
                break;
            case EXCEL:
                file = helper.exportOneFile(title);
                break;
            default:
                throw new ExcelException("不支持的导出类型");
        }
        return WebUtils.baseUrl(WebContext.getRequest()).concat("/mx/export?fileName=" + file.getName());
    }

    public String exportAli(String title, Class<T> voClass, Type type, PageBean pageBean, IExportData<T> iExportData) throws Exception {
        return exportAli(title, voClass, type, pageBean, null, null, iExportData);
    }

    public String exportJxls(String title, PageBean pageBean, Class<?> funClass, String zipFilePath, String excelFilePath, String templateFilePath, IExportData<T> iExportData) throws Exception {
        ExcelExportHelper helper = new ExcelExportHelper().init(funClass, templateFilePath, excelFilePath, zipFilePath);
        while (true) {
            List<T> resultSet = iExportData.exportDataList(pageBean);
            if (ListUtils.isNotEmpty(resultSet)) {
                List<Map<String, Object>> list = new ArrayList<>();
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("data", list);
                helper.addData(param);
                int page = pageBean.getPage();
                page++;
                pageBean.setPage(page);
            } else {
                break;
            }
        }
        File file = helper.export(title);
        return WebUtils.baseUrl(WebContext.getRequest()).concat("/mx/export?fileName=" + file.getName());
    }

    public String exportJxls(String title, PageBean pageBean, Class<?> funClass, String templateFilePath, IExportData<T> iExportData) throws Exception {
        return exportJxls(title, pageBean, funClass, null, null, templateFilePath, iExportData);
    }


}
