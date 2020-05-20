package com.dpstudio.dev.excel;


import com.alibaba.excel.EasyExcel;
import com.dpstudio.dev.utils.FileUtils;
import net.ymate.platform.core.util.DateTimeUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 徐建鹏
 * @Date 2019.02.10.
 * @Time: 14:30.
 * @Description: Excel文件导出助手类
 */
public class ExcelAliExportHelper implements Closeable {

    private List<List<?>> resultData;
    //excle临时文件目录
    private String excleFilePath;
    //zip临时文件目录
    private String zipFilePath;

    private static Class<?> dataClass;

    private ExcelAliExportHelper(String excleFilePath, String zipFilePath) {
        this.excleFilePath = excleFilePath;
        this.zipFilePath = zipFilePath;
    }

    public static ExcelAliExportHelper init(String excleFilePath, String zipFilePath) {
        return new ExcelAliExportHelper(excleFilePath, zipFilePath);
    }

    public static ExcelAliExportHelper init(Class<?> classes) {
        dataClass= classes;
        String excleFilePath = RuntimeUtils.getRootPath() + File.separator + "export" + File.separator;
        String zipFilePath = RuntimeUtils.getRootPath() + File.separator + "zip" + File.separator;
        return new ExcelAliExportHelper(excleFilePath, zipFilePath);
    }

    public ExcelAliExportHelper addData(List<?> data) {
        if (resultData == null) {
            resultData = new ArrayList<>();
        }
        resultData.add(data);
        return this;
    }

    public File export(String fileName) throws Exception {
        FileUtils.fixAndMkDir(excleFilePath);
        FileUtils.fixAndMkDir(zipFilePath);
        List<File> files = new ArrayList<>();
        if (resultData != null && resultData.size() > 0) {
            for (int idx = 0; ; idx++) {
                if (resultData.size() <= idx) {
                    break;
                }
                List<?> data = resultData.get(idx);
                if (data == null || data.isEmpty()) {
                    break;
                }
                File outFile = new File(excleFilePath, fileName + idx +DateTimeUtils.formatTime( DateTimeUtils.currentTimeMillis(),"yyyyMMddHHmmss") + ".xlsx");

                EasyExcel.write(outFile.getPath(), dataClass).sheet(fileName+idx).doWrite(data);
                //输出信息
                files.add(outFile);
            }
        }
        return toZip(files, fileName);
    }

    private File toZip(List<File> files, String fileName) throws Exception {
        File zipFile = new File(zipFilePath, fileName + DateTimeUtils.formatTime(DateTimeUtils.currentTimeMillis(), "yyyyMMdd-HHmmss") + ".zip");
        ZipOutputStream _outputStream = null;
        try {
            _outputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (File file : files) {
                ZipEntry _zipEntry = new ZipEntry(file.getName());
                _outputStream.putNextEntry(_zipEntry);
                //
                InputStream _inputStream = null;
                try {
                    _inputStream = new FileInputStream(file);
                    IOUtils.copyLarge(_inputStream, _outputStream);
                } finally {
                    IOUtils.closeQuietly(_inputStream);
                }
            }
        } finally {
            IOUtils.closeQuietly(_outputStream);
        }
        return zipFile;
    }

    //获取jxls模版文件
    private static File getTemplate(String path) {
        File template = new File(path);
        if (template.exists()) {
            return template;
        }
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
