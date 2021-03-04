package com.dpstudio.dev.excel;


import com.alibaba.excel.EasyExcel;
import com.dpstudio.dev.excel.analysis.IExportHelper;
import net.ymate.platform.commons.util.DateTimeUtils;
import net.ymate.platform.commons.util.RuntimeUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author mengxiang
 * @Date 2019.02.10.
 * @Time: 14:30.
 * @Description: Excel文件导出助手类
 */
public class ExcelAliExportHelper<T> extends IExportHelper implements Closeable {

    /**
     * 导出数据
     */
    private List<List<T>> resultData;


    private Class<?> instance;


    public ExcelAliExportHelper() {
    }

    public ExcelAliExportHelper<T> init(Class<?> classes, String excelFilePath, String zipFilePath) {
        this.instance = classes;
        if(StringUtils.isNotBlank(excelFilePath)){
            EXCEL_FILE_PATH = excelFilePath;
        }
        if(StringUtils.isNotBlank(zipFilePath)){
            ZIP_FILE_PATH = zipFilePath;
        }
        return new ExcelAliExportHelper<>();
    }


    public void addData(List<T> data) {
        if (resultData == null) {
            resultData = new ArrayList<>();
        }
        resultData.add(data);
    }

    /**
     * 导出一个文件  可能会占用cpu和内存  不建议使用
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public File exportOneFile(String fileName) throws Exception {
        fixAndMkDir(EXCEL_FILE_PATH);
        fileName = fileName + DateTimeUtils.formatTime(DateTimeUtils.currentTimeMillis(), "yyyyMMddHHmmss");
        File outFile = new File(EXCEL_FILE_PATH, fileName + ".xlsx");
        if (resultData != null && resultData.size() > 0) {
            List<T> data = new ArrayList<>();
            for (int idx = 0; ; idx++) {
                if (resultData.size() <= idx) {
                    break;
                }
                List<T> tempData = resultData.get(idx);
                if (tempData == null || tempData.isEmpty()) {
                    break;
                }

                data.addAll(tempData);
            }
            EasyExcel.write(outFile.getPath(), instance).sheet(fileName).doWrite(data);
        }
        return outFile;
    }

    public File export(String fileName) throws Exception {
        fixAndMkDir(EXCEL_FILE_PATH);
        fixAndMkDir(ZIP_FILE_PATH);
        List<File> files = new ArrayList<>();
        if (resultData != null && resultData.size() > 0) {
            for (int idx = 0; ; idx++) {
                if (resultData.size() <= idx) {
                    break;
                }
                List<T> data = resultData.get(idx);
                if (data == null || data.isEmpty()) {
                    break;
                }
                File outFile = new File(EXCEL_FILE_PATH, fileName + idx + DateTimeUtils.formatTime(DateTimeUtils.currentTimeMillis(), "yyyyMMddHHmmss") + ".xlsx");

                EasyExcel.write(outFile.getPath(), instance).sheet(fileName + idx).doWrite(data);
                //输出信息
                files.add(outFile);
            }
        }
        return toZip(files, fileName);
    }

    private File toZip(List<File> files, String fileName) throws Exception {
        File zipFile = new File(ZIP_FILE_PATH, fileName + DateTimeUtils.formatTime(DateTimeUtils.currentTimeMillis(), "yyyyMMdd-HHmmss") + ".zip");
        try (ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (File file : files) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                outputStream.putNextEntry(zipEntry);
                try (InputStream inputStream = new FileInputStream(file)) {
                    IOUtils.copyLarge(inputStream, outputStream);
                }
            }
        }
        return zipFile;
    }

    @Override
    public void close() throws IOException {

    }

    public static String fixAndMkDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!dir.endsWith(File.separator)) {
            dir += File.separator;
        }
        return dir;
    }
}
