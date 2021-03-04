package com.dpstudio.dev.excel.export;

import com.dpstudio.dev.code.C;
import com.dpstudio.dev.dto.PageDTO;
import com.dpstudio.dev.excel.ExcelAliExportHelper;
import com.dpstudio.dev.utils.ListUtils;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.util.WebUtils;

import java.io.File;
import java.util.List;

/**
 * @Author: 徐建鹏.
 * @create: 2021-03-04 15:48
 * @Description:
 */
public class ExportHelper<T> {

    public interface IExportData<T> {
        List<T> exportDataList(PageDTO pageDTO) throws Exception;
    }

    public String exportAli(String title, T exportVo, PageDTO pageDTO, String zipFilePath, String excelFilePath, IExportData<T> iExportData) throws Exception {
        ExcelAliExportHelper<T> helper = new ExcelAliExportHelper<T>().init(exportVo.getClass(), excelFilePath, zipFilePath);
        while (true) {
            List<T> resultSet = iExportData.exportDataList(pageDTO);
            if (ListUtils.isNotEmpty(resultSet)) {
                helper.addData(resultSet);
                int page = pageDTO.getPage();
                page++;
                pageDTO.setPage(page);
            } else {
                break;
            }
        }
        File file = helper.exportOneFile(title);
        return WebUtils.baseUrl(WebContext.getRequest()).concat("/dpstudio/export?name=" + file.getName());
    }

    public String exportAli(String title, T exportVo, PageDTO pageDTO, IExportData<T> iExportData) throws Exception {
        return exportAli(title, exportVo, pageDTO, null, null, iExportData);
    }


}
