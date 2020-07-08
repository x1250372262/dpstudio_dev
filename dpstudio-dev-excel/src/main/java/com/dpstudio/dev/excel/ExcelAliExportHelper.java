package com.dpstudio.dev.excel;


import com.alibaba.excel.EasyExcel;
import net.ymate.platform.commons.util.DateTimeUtils;
import net.ymate.platform.commons.util.RuntimeUtils;
import org.apache.commons.io.IOUtils;

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
public class ExcelAliExportHelper<T> implements Closeable {

    /**
     * 导出数据
     */
    private List<List<T>> resultData;
    /**
     * excel临时文件目录
     */
    private final String excelFilePath;
    /**
     * zip临时文件目录
     */
    private final String zipFilePath;

    private static Class<?> instance;

    private ExcelAliExportHelper(String excelFilePath, String zipFilePath) {
        this.excelFilePath = excelFilePath;
        this.zipFilePath = zipFilePath;
    }

    public static ExcelAliExportHelper init(String excelFilePath, String zipFilePath) {
        return new ExcelAliExportHelper(excelFilePath, zipFilePath);
    }

    public static ExcelAliExportHelper init(Class<?> classes) {
        instance = classes;
        String excelFilePath = RuntimeUtils.getRootPath() + File.separator + "export" + File.separator;
        String zipFilePath = RuntimeUtils.getRootPath() + File.separator + "zip" + File.separator;
        return new ExcelAliExportHelper(excelFilePath, zipFilePath);
    }

    public ExcelAliExportHelper addData(List<T> data) {
        if (resultData == null) {
            resultData = new ArrayList<>();
        }
        resultData.add(data);
        return this;
    }

    /**
     * 导出一个文件  可能会占用cpu和内存  不建议使用
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public File exportOneFile(String fileName) throws Exception {
        fixAndMkDir(excelFilePath);
        fileName = fileName + DateTimeUtils.formatTime(DateTimeUtils.currentTimeMillis(), "yyyyMMddHHmmss");
        File outFile = new File(excelFilePath, fileName + ".xlsx");
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
        fixAndMkDir(excelFilePath);
        fixAndMkDir(zipFilePath);
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
                File outFile = new File(excelFilePath, fileName + idx + DateTimeUtils.formatTime(DateTimeUtils.currentTimeMillis(), "yyyyMMddHHmmss") + ".xlsx");

                EasyExcel.write(outFile.getPath(), instance).sheet(fileName + idx).doWrite(data);
                //输出信息
                files.add(outFile);
            }
        }
        return toZip(files, fileName);
    }

    private File toZip(List<File> files, String fileName) throws Exception {
        File zipFile = new File(zipFilePath, fileName + DateTimeUtils.formatTime(DateTimeUtils.currentTimeMillis(), "yyyyMMdd-HHmmss") + ".zip");
        ZipOutputStream outputStream = null;
        try {
            outputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (File file : files) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                outputStream.putNextEntry(zipEntry);
                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(file);
                    IOUtils.copyLarge(inputStream, outputStream);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }
        } finally {
            if (outputStream != null) {
                outputStream.close();
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
