package com.dpstudio.dev.excel.export;


import com.dpstudio.dev.excel.exception.ExcelException;
import com.dpstudio.dev.utils.FileUtils;
import net.ymate.platform.commons.util.DateTimeUtils;
import net.ymate.platform.commons.util.RuntimeUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.formula.FastFormulaProcessor;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiContext;
import org.jxls.transform.poi.PoiTransformer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
public class GridExportHelper implements Closeable {

    private List<Map<String, Object>> resultData;

    private List<String> initHeaders;
    private List<String> endHeaders;
    private String props;

    /**
     * 模板文件路径
     */
    private final String templatePath;
    /**
     * excel临时文件目录
     */
    private final String excelFilePath;
    /**
     * zip临时文件目录
     */
    private final String zipFilePath;

    private GridExportHelper(String templatePath, String excelFilePath, String zipFilePath) {
        this.templatePath = templatePath;
        this.excelFilePath = excelFilePath;
        this.zipFilePath = zipFilePath;
    }

    public static GridExportHelper init(String templatePath, String excelFilePath, String zipFilePath) {
        return new GridExportHelper(templatePath, excelFilePath, zipFilePath);
    }

    public static GridExportHelper init(String templatePath) {
        String excelFilePath = RuntimeUtils.getRootPath() + File.separator + "export" + File.separator;
        String zipFilePath = RuntimeUtils.getRootPath() + File.separator + "zip" + File.separator;
        return new GridExportHelper(templatePath, excelFilePath, zipFilePath);
    }

    private Boolean checkInitHeaders() {
        return initHeaders != null && initHeaders.size() > 0;
    }

    private Boolean checkEndHeaders() {
        return endHeaders != null && endHeaders.size() > 0;
    }

    public GridExportHelper addData(Map<String, Object> data) {
        if (resultData == null) {
            resultData = new ArrayList<>();
        }
        if (data != null) {
            List<String> headers = (List<String>) data.get("headers");
            List<String> tempHeaders = headers;
            //初始和结束都存在
            if (checkInitHeaders() && checkEndHeaders()) {
                tempHeaders = new ArrayList<>(initHeaders);
                tempHeaders.addAll(headers);
                tempHeaders.addAll(endHeaders);
            } else if (checkInitHeaders() && !checkEndHeaders()) {
                //初始存在和结束不存在
                tempHeaders = new ArrayList<>(initHeaders);
                tempHeaders.addAll(headers);
            } else if (!checkInitHeaders() && checkEndHeaders()) {
                //初始不存在和结束存在
                tempHeaders = headers;
                tempHeaders.addAll(endHeaders);
            }
            data.put("headers", tempHeaders);
            resultData.add(data);
        }
        return this;
    }

    public GridExportHelper initProps(String propStr) {
        this.props = propStr;
        return this;
    }

    public GridExportHelper initHeaders(String... header) {
        this.initHeaders = new ArrayList<>(Arrays.asList(header));
        return this;
    }

    public GridExportHelper endHeaders(String... header) {
        this.endHeaders = new ArrayList<>(Arrays.asList(header));
        return this;
    }


    public File export(String fileName, String cellRef) throws Exception {
        FileUtils.fixAndMkDir(excelFilePath);
        FileUtils.fixAndMkDir(zipFilePath);
        //输入信息
        File inFile = getTemplate(templatePath);
        if (inFile == null) {
            throw new ExcelException("Excel 模板未找到。");
        }

        List<File> files = new ArrayList<>();
        if (resultData != null && resultData.size() > 0) {
            for (int idx = 0; idx < resultData.size(); idx++) {
                Map<String, Object> data = resultData.get(idx);
                if (data == null || data.isEmpty()) {
                    break;
                }
                //输出信息
                File outFile = new File(excelFilePath, fileName + idx + ".xlsx");
                if (StringUtils.isNotBlank(cellRef) && data.get("headers") != null) {
                    InputStream is = new FileInputStream(inFile);
                    files.add(exportExcel(is, outFile, data, cellRef));
                }
            }
        }
        return toZip(files, fileName);

    }

    private File exportExcel(InputStream is, File outFile, Map<String, Object> params, String cellRef) throws Exception {
        OutputStream os = new FileOutputStream(outFile);
//        //参数信息
        Context context = new PoiContext();
        if (!params.isEmpty()) {
            for (String key : params.keySet()) {
                context.putVar(key, params.get(key));
            }
        }
        Transformer transformer = PoiTransformer.createTransformer(is, os);
        AreaBuilder areaBuilder = new XlsCommentAreaBuilder(transformer);
        List<Area> xlsAreaList = areaBuilder.build();
        Area xlsArea = xlsAreaList.get(0);
        xlsArea.applyAt(new CellRef(cellRef), context);
        xlsArea.setFormulaProcessor(new FastFormulaProcessor());
        xlsArea.processFormulas();
        transformer.write();
        return outFile;
    }

    private File toZip(List<File> files, String fileName) throws Exception {
//        String zipFileName = DigestUtils.md5Hex(fileName + DateTimeUtils.formatTime(DateTimeUtils.currentTimeMillis(), "yyyyMMdd-HHmmss"));
        File zipFile = new File(zipFilePath, fileName + DateTimeUtils.formatTime(DateTimeUtils.currentTimeMillis(), "yyyyMMdd-HHmmss") + ".zip");
        ZipOutputStream outputStream = null;
        try {
            outputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (File file : files) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                outputStream.putNextEntry(zipEntry);
                //
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

    /**
     * 获取jxls模版文件
     * @param path
     * @return
     */
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

    public List<String> getInitHeaders() {
        return initHeaders;
    }

    public void setInitHeaders(List<String> initHeaders) {
        this.initHeaders = initHeaders;
    }

    public List<String> getEndHeaders() {
        return endHeaders;
    }

    public void setEndHeaders(List<String> endHeaders) {
        this.endHeaders = endHeaders;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }
}
