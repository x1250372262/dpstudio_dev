package com.dpstudio.dev.excel;


import com.dpstudio.dev.excel.exception.ExcelException;
import com.dpstudio.dev.utils.FileUtils;
import net.ymate.platform.commons.util.DateTimeUtils;
import net.ymate.platform.commons.util.RuntimeUtils;
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
public class ExcelExportHelper implements Closeable {

    private final Class<?> funClass;

    private List<Map<String, Object>> resultData;

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

    private ExcelExportHelper(Class<?> funcClass, String templatePath, String excelFilePath, String zipFilePath) {
        this.funClass = funcClass;
        this.templatePath = templatePath;
        this.excelFilePath = excelFilePath;
        this.zipFilePath = zipFilePath;
    }

    public static ExcelExportHelper init(Class<?> funcClass, String templatePath, String excelFilePath, String zipFilePath) {
        return new ExcelExportHelper(funcClass, templatePath, excelFilePath, zipFilePath);
    }

    public static ExcelExportHelper init(Class<?> funcClass, String templatePath) {
        String excelFilePath = RuntimeUtils.getRootPath() + File.separator + "export" + File.separator;
        String zipFilePath = RuntimeUtils.getRootPath() + File.separator + "zip" + File.separator;
        return new ExcelExportHelper(funcClass, templatePath, excelFilePath, zipFilePath);
    }

    public ExcelExportHelper addData(Map<String, Object> data) {
        if (resultData == null) {
            resultData = new ArrayList<>();
        }
        resultData.add(data);
        return this;
    }

    public File export(String fileName) throws Exception {
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
                files.add(exportExcel(is, outFile, data));
            }
        }
        return toZip(files, fileName);

    }

    private File exportExcel(InputStream is, File outFile, Map<String, Object> params) throws Exception {
        OutputStream os = new FileOutputStream(outFile);
        //参数信息
        Context context = PoiTransformer.createInitialContext();
        if (!params.isEmpty()) {
            for (String key : params.keySet()) {
                context.putVar(key, params.get(key));
            }
        }
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Transformer transformer = jxlsHelper.createTransformer(is, os);
        //获得配置
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
        //设置静默模式，不报警告
        //函数强制，自定义功能
        Map<String, Object> funcs = new HashMap<>();
        //添加自定义功能
        funcs.put("utils", funClass.newInstance());
        JexlEngine customJexlEngine = new JexlBuilder().namespaces(funcs).create();
        evaluator.setJexlEngine(customJexlEngine);
        //必须要这个，否者表格函数统计会错乱
        jxlsHelper.setUseFastFormulaProcessor(false).processTemplate(context, transformer);
        return outFile;
    }

    private File toZip(List<File> files, String fileName) throws Exception {
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
}
