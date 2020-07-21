package com.dpstudio.dev.generator.generate.impl;

import com.dpstudio.dev.core.R;
import com.dpstudio.dev.generator.bean.Attr;
import com.dpstudio.dev.generator.bean.ColumnInfo;
import com.dpstudio.dev.generator.bean.ConfigInfo;
import com.dpstudio.dev.generator.bean.TableInfo;
import com.dpstudio.dev.generator.generate.BaseGenerate;
import com.dpstudio.dev.generator.generate.ICurdGenerate;
import com.dpstudio.dev.generator.generate.IVoGenerate;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.core.util.UUIDUtils;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.jdbc.IDatabase;
import net.ymate.platform.persistence.jdbc.JDBC;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: 徐建鹏.
 * @Date: 2019/11/5.
 * @Time: 12:00 下午.
 * @Description:
 */
public class CurdVoGenerateImpl extends BaseGenerate implements ICurdGenerate {

    /**
     * 临时文件路径
     */
    private final String tempFilePath = BaseGenerate.tempFilePath.concat("curd").concat(File.separator);

    /**
     * 压缩包文件路径
     */
    private final String zipFilePath = BaseGenerate.zipFilePath.concat("curd").concat(File.separator);

    private static final Log LOG = LogFactory.getLog(CurdVoGenerateImpl.class);

    private final IDatabase iDatabase;

    private final ConfigInfo configInfo;

    private final Configuration freemarkerConfig;

    public CurdVoGenerateImpl(ConfigInfo configInfo) throws IOException {
        iDatabase = JDBC.get(YMP.get());
        this.configInfo = configInfo;
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_22);
        freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        freemarkerConfig.setClassForTemplateLoading(CurdVoGenerateImpl.class, "/");
        freemarkerConfig.setDefaultEncoding("UTF-8");
    }

    @Override
    public R create() throws Exception {
        IVoGenerate iVoGenerate = new VoGenerateImpl(configInfo);
        R r = iVoGenerate.create();
        if (!r.check()) {
            return r;
        }
        List<String> tables = configInfo.getTableList();
        List<File> fileList = new ArrayList<>();
        if (tables.isEmpty()) {
            tables = TableInfo.getTableNames(iDatabase);
        }
        for (String tableName : tables) {
            build(fileList, TableInfo.create(iDatabase.getDefaultConnectionHolder(), configInfo, tableName), tableName);
        }
        if (fileList.isEmpty()) {
            return R.ok().attr("download", 0);
        }
        File zipFile = toZip(fileList);
        return R.ok().attr("download", 1).attr("zipFile", zipFile);
    }

    private File toZip(List<File> files) throws Exception {
        String zipFileName = UUIDUtils.UUID();
        File zipFile = new File(zipFilePath, zipFileName + ".zip");
        File path = zipFile.getParentFile();
        if (!path.exists()) {
            path.mkdirs();
        }
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
                    IOUtils.closeQuietly(inputStream);
                }
            }
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return zipFile;
    }

    /**
     * 创建ftl参数集合
     */
    @Override
    protected Map<String, Object> buildPropMap() {
        Map<String, Object> propMap = super.buildPropMap();
        //实体类包名
        propMap.put("voPackageName", configInfo.getVoPackageName());
        propMap.put("modelPackageName", configInfo.getModelPackageName());
        propMap.put("daoPackageName", configInfo.getDaoPackageName());
        propMap.put("servicePackageName", configInfo.getServicePackageName());
        propMap.put("controllerPackageName", configInfo.getControllerPackageName());
        return propMap;
    }

    private void build(List<File> fileList, TableInfo tableInfo, String tableName) {
        if (tableInfo != null) {
            //生成实体的参数
            Map<String, Object> propMap = buildPropMap();
            for (String prefix : configInfo.getTablePrefixes()) {
                if (tableName.startsWith(prefix)) {
                    tableName = tableName.substring(prefix.length());
                    propMap.put("tableName", tableName);
                    break;
                }
            }
            String modelName = StringUtils.capitalize(EntityMeta.propertyNameToFieldName(tableName));
            propMap.put("modelName", modelName);
            // 用于完整的构造方法
            List<Attr> fieldList = new ArrayList<>();

            for (String key : tableInfo.getFieldMap().keySet()) {
                ColumnInfo ci = tableInfo.getFieldMap().get(key);
                Attr attr = ci.toAttr();
                String varType = attr.getVarType();
                attr.setVarType(varType.substring(varType.lastIndexOf(".") + 1));
                attr.setColumnName(ci.getColumnName().toUpperCase());
                fieldList.add(attr);
            }
            propMap.put("fieldList", fieldList);
            outFile(fileList, "/I" + modelName + "Dao.java", "/dao.ftl", propMap, 0, false);
            outFile(fileList, "/" + modelName + "DaoImpl.java", "/dao-impl.ftl", propMap, 0, true);

            outFile(fileList, "/I" + modelName + "Service.java", "/service.ftl", propMap, 1, false);
            outFile(fileList, "/" + modelName + "ServiceImpl.java", "/service-impl.ftl", propMap, 1, true);

            outFile(fileList, "/" + modelName + "Controller.java", "/controller.ftl", propMap, 2, false);
        }
    }

    /**
     * 输出文件
     *
     * @param fileList       文件集合
     * @param targetFileName 目标文件
     * @param tmplFile       模板文件
     * @param propMap        参数集合
     * @param type           类型
     * @param isImpl         是否实现类
     */
    private void outFile(List<File> fileList, String targetFileName, String tmplFile, Map<String, Object> propMap, int type, boolean isImpl) {
        Writer outWriter = null;
        try {
            String outPath = "${root}";
            if (configInfo.isDownLoad()) {
                outPath = tempFilePath;
            } else {
                String tPath = RuntimeUtils.replaceEnvVariable(outPath);
                outPath = tPath.substring(0, tPath.indexOf("target")).concat("src/main/java");
            }
            String filePath = "";
            if (type == 0) {
                filePath = configInfo.getDaoPackageName().replace('.', '/');
                if (isImpl) {
                    filePath = configInfo.getDaoPackageName().concat(".impl").replace('.', '/');
                }
            } else if (type == 1) {
                filePath = configInfo.getServicePackageName().replace('.', '/');
                if (isImpl) {
                    filePath = configInfo.getServicePackageName().concat(".impl").replace('.', '/');
                }
            } else if (type == 2) {
                filePath = configInfo.getControllerPackageName().replace('.', '/');
            }
            File outputFile = new File(outPath,
                    new File(filePath, targetFileName).getPath());
            File path = outputFile.getParentFile();
            if (path.exists() || path.mkdirs()) {
                outWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StringUtils.defaultIfEmpty(freemarkerConfig.getOutputEncoding(), freemarkerConfig.getDefaultEncoding())));
                String templateRootPath = "com/dpstudio/generator/template/curdvo/";
                freemarkerConfig.getTemplate(templateRootPath + tmplFile).process(propMap, outWriter);
                out("curd输出路径" + outputFile);
                if (configInfo.isDownLoad()) {
                    fileList.add(outputFile);
                }
            }
        } catch (Exception e) {
            LOG.warn("", RuntimeUtils.unwrapThrow(e));
        } finally {
            IOUtils.closeQuietly(outWriter);
        }
    }

}
