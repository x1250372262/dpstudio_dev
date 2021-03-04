package com.dpstudio.dev.excel.analysis;

import net.ymate.platform.commons.util.RuntimeUtils;

import java.io.File;

/**
 * @Author: 徐建鹏.
 * @create: 2021-03-04 16:25
 * @Description:
 */
public class IExportHelper {

    /**
     * excel临时文件目录
     */
    public static String EXCEL_FILE_PATH = RuntimeUtils.getRootPath() + File.separator + "export" + File.separator;

    /**
     * zip临时文件目录
     */
    public static String ZIP_FILE_PATH = RuntimeUtils.getRootPath() + File.separator + "zip" + File.separator;


}
