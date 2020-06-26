package com.dpstudio.dev.generator.generate.impl;

import com.dpstudio.dev.core.R;
import com.dpstudio.dev.generator.bean.Attr;
import com.dpstudio.dev.generator.bean.ColumnInfo;
import com.dpstudio.dev.generator.bean.ConfigInfo;
import com.dpstudio.dev.generator.bean.TableInfo;
import com.dpstudio.dev.generator.generate.BaseGenerate;
import com.dpstudio.dev.generator.generate.IModelGenerate;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.fusesource.jansi.Ansi.Color.YELLOW;

/**
 * @Author: 徐建鹏.
 * @Date: 2019/11/5.
 * @Time: 12:00 下午.
 * @Description:
 */
public class ModelGenerateImpl extends BaseGenerate implements IModelGenerate {

    /**
     * 临时文件路径
     */
    private final String tempFilePath = BaseGenerate.tempFilePath.concat("model").concat(File.separator);

    /**
     * 压缩包文件路径
     */
    private final String zipFilePath = BaseGenerate.zipFilePath.concat("model").concat(File.separator);

    private static final Log LOG = LogFactory.getLog(ModelGenerateImpl.class);

    private final IDatabase iDatabase;

    private final ConfigInfo configInfo;

    private final Configuration freemarkerConfig;

    public ModelGenerateImpl(ConfigInfo configInfo) throws IOException {
        iDatabase = JDBC.get(YMP.get());
        this.configInfo = configInfo;
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_22);
        freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        freemarkerConfig.setClassForTemplateLoading(ModelGenerateImpl.class, "/");
        freemarkerConfig.setDefaultEncoding("UTF-8");
    }

    @Override
    public R create() throws Exception {
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
        if(!path.exists()){
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
     *
     * @return
     */
    @Override
    protected Map<String, Object> buildPropMap() {
        Map<String, Object> propMap = super.buildPropMap();
        //实体类包名
        propMap.put("packageName", configInfo.modelPackageName());
        propMap.put("isOutGetSet", configInfo.isOutGetSet());
        return propMap;
    }

    private void build(List<File> fileList, TableInfo tableInfo, String tableName) {
        if (tableInfo != null) {
            //生成实体的参数
            Map<String, Object> propMap = buildPropMap();
            propMap.put("tableName", tableName);
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
            // 用于非空字段的构造方法
            List<Attr> fieldListForNotNullable = new ArrayList<>();
            // 用于生成字段名称常量
            List<Attr> allFieldList = new ArrayList<>();
            if (tableInfo.getPkSet().size() > 1) {
                propMap.put("primaryKeyType", modelName + "PK");
                propMap.put("primaryKeyName", StringUtils.uncapitalize((String) propMap.get("primaryKeyType")));
                List<Attr> primaryKeyList = new ArrayList<>();
                propMap.put("primaryKeyList", primaryKeyList);
                Attr pkAttr = new Attr((String) propMap.get("primaryKeyType"), (String) propMap.get("primaryKeyName"), null, false, false, 0, 0, false, null, null);
                fieldList.add(pkAttr);
                fieldListForNotNullable.add(pkAttr);
                for (String pkey : tableInfo.getPkSet()) {
                    ColumnInfo ci = tableInfo.getFieldMap().get(pkey);
                    Attr attr = ci.toAttr();
                    primaryKeyList.add(attr);
                    allFieldList.add(new Attr("String", ci.getColumnName().toUpperCase(),
                            ci.getColumnName(),
                            ci.isAutoIncrement(),
                            ci.isSigned(),
                            ci.getPrecision(),
                            ci.getScale(),
                            ci.isNullable(),
                            ci.getDefaultValue(),
                            ci.getRemarks()));
                }
                for (String key : tableInfo.getFieldMap().keySet()) {
                    if (tableInfo.getPkSet().contains(key)) {
                        continue;
                    }
                    ColumnInfo ci = tableInfo.getFieldMap().get(key);
                    Attr attr = ci.toAttr();
                    fieldList.add(attr);
                    fieldList.add(attr);
                    fieldListForNotNullable.add(attr);
                    allFieldList.add(new Attr("String", ci.getColumnName().toUpperCase(),
                            ci.getColumnName(),
                            ci.isAutoIncrement(),
                            ci.isSigned(),
                            ci.getPrecision(),
                            ci.getScale(),
                            ci.isNullable(),
                            ci.getDefaultValue(),
                            ci.getRemarks()));
                }
            } else {
                ColumnInfo tmpCi = tableInfo.getFieldMap().get("id");
                propMap.put("primaryKeyType", tmpCi == null ? "Serializable" : tmpCi.getColumnType());
                propMap.put("primaryKeyName", "id");
                for (String key : tableInfo.getFieldMap().keySet()) {
                    ColumnInfo ci = tableInfo.getFieldMap().get(key);
                    Attr attr = ci.toAttr();
                    fieldList.add(attr);
                    if (!attr.isNullable()) {
                        fieldListForNotNullable.add(attr);
                    }
                    allFieldList.add(new Attr("String", ci.getColumnName().toUpperCase(),
                            ci.getColumnName(),
                            ci.isAutoIncrement(),
                            ci.isSigned(),
                            ci.getPrecision(),
                            ci.getScale(),
                            ci.isNullable(),
                            ci.getDefaultValue(),
                            ci.getRemarks()));
                }
            }
            propMap.put("fieldList", fieldList);
            propMap.put("notNullableFieldList", fieldList.size() == fieldListForNotNullable.size() ? Collections.emptyList() : fieldListForNotNullable);
            propMap.put("allFieldList", allFieldList);
            outFile(fileList, "/" + modelName + ".java", "/Entity.ftl", propMap);
            if (tableInfo.getPkSet().size() > 1) {
                propMap.put("modelName", modelName);
                if (tableInfo.getPkSet().size() > 1) {
                    List<Attr> primaryKeyList = new ArrayList<Attr>();
                    propMap.put("primaryKeyList", primaryKeyList);
                    //
                    for (String pkey : tableInfo.getPkSet()) {
                        ColumnInfo ci = tableInfo.getFieldMap().get(pkey);
                        primaryKeyList.add(ci.toAttr());
                    }
                }
                outFile(fileList, "/" + modelName + "PK.java", "/EntityPK.ftl", propMap);
            }
        }
    }

    /**
     * 输出文件
     *
     * @param targetFileName
     * @param tmplFile
     * @param propMap
     */
    private void outFile(List<File> fileList, String targetFileName, String tmplFile, Map<String, Object> propMap) {
        Writer outWriter = null;
        try {
            String outPath = "${root}";
            if (configInfo.isDownLoad()) {
                outPath = tempFilePath;
            } else {
                String tPath = RuntimeUtils.replaceEnvVariable(outPath);
                outPath = tPath.substring(0, tPath.indexOf("target")).concat("src/main/java");
            }
            File outputFile = new File(outPath,
                    new File(configInfo.getModelPackageName().replace('.', '/'), targetFileName).getPath());
            File path = outputFile.getParentFile();
            if (path.exists() || path.mkdirs()) {
                outWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StringUtils.defaultIfEmpty(freemarkerConfig.getOutputEncoding(), freemarkerConfig.getDefaultEncoding())));
                String templateRootPath = "com/dpstudio/generator/template/";
                freemarkerConfig.getTemplate(templateRootPath + tmplFile).process(propMap, outWriter);
                out(YELLOW, "输出路径" + outputFile);
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
