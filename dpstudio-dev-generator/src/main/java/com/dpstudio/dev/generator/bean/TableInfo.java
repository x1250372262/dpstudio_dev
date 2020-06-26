package com.dpstudio.dev.generator.bean;

import com.dpstudio.dev.generator.generate.BaseGenerate;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import net.ymate.platform.persistence.jdbc.IDatabase;
import net.ymate.platform.persistence.jdbc.base.impl.ArrayResultSetHandler;
import net.ymate.platform.persistence.jdbc.query.SQL;
import net.ymate.platform.persistence.jdbc.support.ResultSetHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.fusesource.jansi.Ansi.Color.RED;

/**
 * @Author: 徐建鹏.
 * @Date: 2019/11/5.
 * @Time: 12:00 下午.
 * @Description: 根据ymp框架仿写的表信息类
 */
public class TableInfo {

    private static final Log LOG = LogFactory.getLog(TableInfo.class);

    public static List<String> getTableNames(IDatabase database) throws Exception {
        return database.openSession(session -> {
            String sql = "show full tables where Table_type='BASE TABLE'";
            final List<String> results = new ArrayList<>();
            ResultSetHelper helper = ResultSetHelper.bind(session.find(SQL.create(sql),new ArrayResultSetHandler()));
            if (helper != null) {
                helper.forEach((wrapper, row) -> {
                    results.add(wrapper.getAsString(0));
                    return true;
                });
            }
            return results;
        });
    }

    public static TableInfo create(IConnectionHolder connectionHolder, ConfigInfo configInfo, String tableName) throws Exception {
        Connection connection = connectionHolder.getConnection();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> primaryKeys = new LinkedList<>();
        try {
            resultSet = databaseMetaData.getPrimaryKeys(configInfo.getDbName(), configInfo.getDbUserName(), tableName);
            if (resultSet == null) {
                BaseGenerate.out(RED, tableName + "表没有设置主键,本次生成忽略");
                return null;
            } else {
                while (resultSet.next()) {
                    primaryKeys.add(resultSet.getString(4).toLowerCase());
                }
                if (primaryKeys.isEmpty()) {
                    BaseGenerate.out(RED, tableName + "表没有设置主键,本次生成忽略");
                    return null;
                }
            }
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("SELECT * FROM ".concat(connectionHolder.getDialect().wrapIdentifierQuote(tableName)));
            return new TableInfo(configInfo.getDbName(), configInfo.getDbUserName(), tableName, primaryKeys, ColumnInfo.create(configInfo, tableName, primaryKeys, databaseMetaData, resultSet.getMetaData()));
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOG.warn("", RuntimeUtils.unwrapThrow(e));
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOG.warn("", e);
                }
            }
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.warn("", e);
            }
        }
    }

    private final String catalog;

    private final String schema;

    private final String name;

    private final List<String> pkSet;

    private final Map<String, ColumnInfo> fieldMap;

    public TableInfo(String catalog, String schema, String name, List<String> pkSet, Map<String, ColumnInfo> fieldMap) {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name;
        this.pkSet = pkSet;
        this.fieldMap = fieldMap;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }

    public List<String> getPkSet() {
        return pkSet;
    }

    public Map<String, ColumnInfo> getFieldMap() {
        return fieldMap;
    }
}
