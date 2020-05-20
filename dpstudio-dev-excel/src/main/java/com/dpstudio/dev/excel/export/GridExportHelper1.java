package com.dpstudio.dev.excel.export;


import com.dpstudio.dev.excel.exception.ExcelException;
import com.dpstudio.dev.utils.FileUtils;
import net.ymate.platform.core.util.DateTimeUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.command.GridCommand;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
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
public class GridExportHelper1 implements Closeable {

    private List<Map<String, Object>> resultData;

    private List<String> initHeaders;
    private List<String> endHeaders;
    private String props;

    //模板文件路径
    private String templatePath;
    //excel临时文件目录
    private String excelFilePath;
    //zip临时文件目录
    private String zipFilePath;

    private GridExportHelper1(String templatePath, String excelFilePath, String zipFilePath) {
        this.templatePath = templatePath;
        this.excelFilePath = excelFilePath;
        this.zipFilePath = zipFilePath;
    }

    public static GridExportHelper1 init(String templatePath, String excelFilePath, String zipFilePath) {
        return new GridExportHelper1(templatePath, excelFilePath, zipFilePath);
    }

    public static GridExportHelper1 init(String templatePath) {
        String excelFilePath = RuntimeUtils.getRootPath() + File.separator + "export" + File.separator;
        String zipFilePath = RuntimeUtils.getRootPath() + File.separator + "zip" + File.separator;
        return new GridExportHelper1(templatePath, excelFilePath, zipFilePath);
    }

    private Boolean checkInitHeaders() {
        return initHeaders != null && initHeaders.size() > 0;
    }

    private Boolean checkEndHeaders() {
        return endHeaders != null && endHeaders.size() > 0;
    }

    public GridExportHelper1 addData(Map<String, Object> data) {
        if (resultData == null) {
            resultData = new ArrayList<>();
        }
        if (data != null) {
            resultData.add(data);
            List<String> headers = (List<String>) data.get("headers");
            List<String> tempHeaders = headers;
            //初始和结束都存在
            if (checkInitHeaders() && checkEndHeaders()) {
                tempHeaders = initHeaders;
                tempHeaders.addAll(headers);
                tempHeaders.addAll(endHeaders);
            } else if (checkInitHeaders() && !checkEndHeaders()) {
                //初始存在和结束不存在
                tempHeaders = initHeaders;
                tempHeaders.addAll(headers);
            } else if (!checkInitHeaders() && checkEndHeaders()) {
                //初始不存在和结束存在
                tempHeaders = headers;
                tempHeaders.addAll(endHeaders);
            }
            data.put("headers", tempHeaders);
        }
        return this;
    }

    public GridExportHelper1 initProps(String propStr) {
        this.props = propStr;
        return this;
    }

    public GridExportHelper1 initHeaders(String... header) {
        this.initHeaders = new ArrayList<>(Arrays.asList(header));
        return this;
    }

    public GridExportHelper1 endHeaders(String... header) {
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
        InputStream is = new FileInputStream(inFile);

        List<File> files = new ArrayList<>();
        if (resultData != null && resultData.size() > 0) {
            for (int idx = 0; ; idx++) {
                if (resultData.size() <= idx) {
                    break;
                }
                Map<String, Object> data = resultData.get(idx);
                if (data == null || data.isEmpty()) {
                    break;
                }
                //输出信息
                File outFile = new File(excelFilePath, fileName + idx + ".xlsx");
                if (StringUtils.isNotBlank(cellRef) && data.get("headers") != null) {
                    files.add(exportExcel(is, outFile, data, cellRef));
                }
            }
        }
        return toZip(files, fileName);

    }

    private File exportExcel(InputStream is, File outFile, Map<String, Object> params, String cellRef) throws Exception {
        OutputStream os = new FileOutputStream(outFile);
        //参数信息
        Context context = PoiTransformer.createInitialContext();
        if (!params.isEmpty()) {
            for (String key : params.keySet()) {
                context.putVar(key, params.get(key));
            }
        }
//        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Workbook workbook = WorkbookFactory.create(is);
        Transformer transformer = PoiTransformer.createSxssfTransformer(workbook);
//        Transformer transformer = jxlsHelper.createTransformer(is, os);
        //获得配置
//        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
        //设置静默模式，不报警告
//        evaluator.getJexlEngine()
        List<String> headers = (List<String>) params.get("headers");
        String headerStr = String.join(",", headers);
//        jxlsHelper.processGridTemplateAtCell(is, os, context, headerStr, cellRef);
        AreaBuilder areaBuilder = new XlsCommentAreaBuilder();
        areaBuilder.setTransformer(transformer);
        List<Area> xlsAreaList = areaBuilder.build();
        Area firstArea = xlsAreaList.get(0);
        CellRef targetCellRef = new CellRef(cellRef);
        GridCommand gridCommand = (GridCommand) firstArea.getCommandDataList().get(0).getCommand();
        gridCommand.setProps(headerStr);
//        gridCommand.setHeaders(headers);
        firstArea.applyAt(targetCellRef, context);
        ((PoiTransformer) transformer).getWorkbook().write(os);
        return outFile;
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
