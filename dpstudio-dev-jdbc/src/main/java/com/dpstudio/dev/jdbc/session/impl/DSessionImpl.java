package com.dpstudio.dev.jdbc.session.impl;

import com.dpstudio.dev.jdbc.session.IDSession;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.persistence.annotation.Property;
import net.ymate.platform.persistence.jdbc.ISession;
import net.ymate.platform.persistence.jdbc.JDBC;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/5/15.
 * @Time: 9:30 上午.
 * @Description:
 */
public class DSessionImpl implements IDSession {

    private static ISession iSession;

    private static String _ds;

    @Override
    public ISession get(String ds) throws Exception {
        _ds = ds;
        return iSession != null ? iSession : JDBC.get().openSession(ds);
    }

    @Override
    public ISession get() throws Exception {
        return iSession != null ? iSession : JDBC.get().openSession();
    }

    private List<Map<String, Object>> getInsertAllDatas(List<?> entities) throws Exception {
        List<Map<String, Object>> datas = new ArrayList<>();
        for (Object entity : entities) {
            Class cls = entity.getClass();
            Field[] fields = cls.getDeclaredFields();
            if (fields != null) {
                Map<String, Object> data = new HashMap<>();
                for (Field field : fields) {
                    if (ClassUtils.isAnnotationOf(field, Property.class)) {
                        Property property = field.getAnnotation(Property.class);
                        field.setAccessible(true);
                        data.put(property.name(), field.get(entity));
                    }
                }
                datas.add(data);
            }
        }
        return datas;
    }

    @Override
    public int insertAll(List<?> entities) throws Exception {
        if(iSession == null){
            if(StringUtils.isNotBlank(_ds)){
                iSession = get(_ds);
            }else{
                iSession = get();
            }
        }
        if (entities != null || entities.size() > 0) {
            Object entity = entities.get(0);
            Class cls = entity.getClass();
            Field field = cls.getField("TABLE_NAME");
            String tableName = (String) field.get(field.getName());
            List<Map<String, Object>> datas = getInsertAllDatas(entities);
            int affectRowCount = -1;
            Connection connection = iSession.getConnectionHolder().getConnection();
            PreparedStatement preparedStatement = null;
            Map<String, Object> valueMap = datas.get(0);
            Set<String> keySet = valueMap.keySet();
            Iterator<String> iterator = keySet.iterator();
            /**要插入的字段sql，其实就是用key拼起来的**/
            StringBuilder columnSql = new StringBuilder();
            /**要插入的字段值，其实就是？**/
            StringBuilder unknownMarkSql = new StringBuilder();
            Object[] values = new Object[valueMap.size()];
            int i = 0;
            while (iterator.hasNext()) {
                String key = iterator.next();
                values[i] = key;
                columnSql.append(i == 0 ? "" : ",");
                columnSql.append(key);

                unknownMarkSql.append(i == 0 ? "" : ",");
                unknownMarkSql.append("?");
                i++;
            }
            /**开始拼插入的sql语句**/
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO `" + iSession.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix().concat(tableName) + "`");
            sql.append(" (");
            sql.append(columnSql);
            sql.append(" )  VALUES (");
            sql.append(unknownMarkSql);
            sql.append(" )");

            /**执行SQL预编译**/
            preparedStatement = connection.prepareStatement(sql.toString());
            /**设置不自动提交，以便于在出现异常的时候数据库回滚**/
            connection.setAutoCommit(false);
            System.out.println(sql.toString());
            for (int j = 0; j < datas.size(); j++) {
                for (int k = 0; k < values.length; k++) {
                    preparedStatement.setObject(k + 1, datas.get(j).get(values[k]));
                }
                preparedStatement.addBatch();
            }
            int[] arr = preparedStatement.executeBatch();
            affectRowCount = arr.length;
            return affectRowCount;
        }
        return 0;
    }
}
