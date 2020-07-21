package com.dpstudio.dev.generator.bean;

import com.dpstudio.dev.generator.generate.BaseGenerate;
import net.ymate.platform.persistence.base.EntityMeta;
import org.apache.commons.lang.StringUtils;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 徐建鹏.
 * @Date: 2019/11/5.
 * @Time: 12:00 下午.
 * @Description: 根据ymp框架仿写的列信息类
 */
public class ColumnInfo {

    public static Map<String, ColumnInfo> create(ConfigInfo configInfo,
                                                 String tableName,
                                                 List<String> primaryKeys,
                                                 DatabaseMetaData databaseMetaData,
                                                 ResultSetMetaData metaData) throws SQLException {
        Map<String, ColumnInfo> returnValue = new LinkedHashMap<>(metaData.getColumnCount());

        BaseGenerate.out(">>> " + "列名称 / " +
                "列类型 / " +
                "是否是主键 / " +
                "备注");
        for (int idx = 1; idx <= metaData.getColumnCount(); idx++) {
            // 获取字段元数据对象
            ResultSet column = databaseMetaData.getColumns(configInfo.getDbName(), configInfo.getDbUserName(), tableName, metaData.getColumnName(idx));
            if (column.next()) {
                BaseGenerate.out("--> " + metaData.getColumnName(idx).toLowerCase() + "\t" +
                        metaData.getColumnClassName(idx) + "\t" +
                        primaryKeys.contains(metaData.getColumnName(idx).toLowerCase()) + "\t" +
                        column.getString("REMARKS"));
                // 提取字段定义及字段默认值
                String name = metaData.getColumnName(idx).toLowerCase();
                ColumnInfo columnInfo = new ColumnInfo(
                        name,
                        metaData.getColumnClassName(idx),
                        metaData.isAutoIncrement(idx),
                        primaryKeys.contains(name),
                        metaData.isSigned(idx),
                        metaData.getPrecision(idx),
                        metaData.getScale(idx),
                        metaData.isNullable(idx),
                        column.getString("COLUMN_DEF"),
                        column.getString("REMARKS"));
                returnValue.put(name, columnInfo);
            }
            column.close();
        }
        return returnValue;
    }

    private String name;

    private final String columnName;

    private final String columnType;

    private final boolean autoIncrement;

    private final boolean primaryKey;

    private final boolean signed;

    private final int precision;

    private final int scale;

    private final boolean nullable;

    private final String defaultValue;

    private final String remarks;

    public ColumnInfo(String columnName, String columnType, boolean autoIncrement, boolean primaryKey, boolean signed, int precision, int scale, int nullable, String defaultValue, String remarks) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.autoIncrement = autoIncrement;
        this.primaryKey = primaryKey;
        this.signed = signed;
        this.precision = precision;
        this.scale = scale;
        this.nullable = nullable > 0;
        this.defaultValue = defaultValue;
        this.remarks = StringUtils.replaceEach(remarks, new String[]{"\"", "\r\n", "\r", "\n", "\t"}, new String[]{"\\\"", "[\\r][\\n]", "[\\r]", "[\\n]", "[\\t]"});
        this.name = StringUtils.uncapitalize(EntityMeta.propertyNameToFieldName(columnName.toLowerCase()));
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public boolean isSigned() {
        return signed;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public boolean isNullable() {
        return nullable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public Attr toAttr() {
        return new Attr(getColumnType(), this.name, getColumnName(), isAutoIncrement(), isSigned(), getPrecision(), getScale(), isNullable(), getDefaultValue(), getRemarks());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
