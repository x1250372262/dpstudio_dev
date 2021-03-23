package com.dpstudio.dev.excel.export;

import com.dpstudio.dev.excel.analysis.IExportHelper;
import com.dpstudio.dev.excel.exception.ExcelException;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.HttpStatusView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @Author: mengxiang.
 * @create: 2021-03-04 16:17
 * @Description:
 */
@Controller
@RequestMapping("/dpstudio/export/")
public class ExportController {


    /**
     * 导出文件
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    @RequestMapping("/")
    public IView export(@VRequired(msg = "文件名称")
                        @RequestParam String fileName) throws Exception {
        IView returnView = null;
        // 判断资源类型
        try {
            String filePath = "";
            if (fileName.endsWith(".zip")) {
                filePath = IExportHelper.ZIP_FILE_PATH;
            } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
                filePath = IExportHelper.EXCEL_FILE_PATH;
            } else {
                throw new ExcelException("不支持的文件类型");
            }
            File zipFile = new File(filePath, fileName);
            if (zipFile.exists()) {
                return View.binaryView(zipFile).useAttachment(zipFile.getName());
            }
        } catch (IllegalArgumentException e) {
            returnView = View.httpStatusView(HttpServletResponse.SC_BAD_REQUEST);
        }
        return returnView == null ? HttpStatusView.NOT_FOUND : returnView;
    }
}
