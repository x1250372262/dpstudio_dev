package com.mx.dev.support.jdbc;

import com.mx.dev.support.jdbc.annotation.ChildBean;
import com.mx.dev.utils.ListUtils;
import com.mx.dev.utils.MapUtils;
import net.ymate.platform.commons.json.JsonWrapper;
import net.ymate.platform.commons.util.ClassUtils;
import net.ymate.platform.core.persistence.base.EntityMeta;
import net.ymate.platform.persistence.jdbc.base.IResultSetHandler;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: mengxiang.
 * @create: 2021-06-25 09:23
 * @Description:
 */
public class BeanChildResultSetHandler<T> implements IResultSetHandler<T> {

    private final Class<T> beanClass;

    private Class<?> childClass;

    private ResultSetMetaData metaData;

    @SuppressWarnings("unchecked")
    public BeanChildResultSetHandler() {
        beanClass = (Class<T>) ClassUtils.getParameterizedTypes(getClass()).get(0);
    }

    public BeanChildResultSetHandler(Class<T> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public List<T> handle(ResultSet resultSet) throws Exception {
        ChildBean childBean = beanClass.getAnnotation(ChildBean.class);
        if (childBean == null || childBean.childClass() == null) {
            throw new RuntimeException("类不包含ChildBean注解或childClass为空");
        }
        childClass = childBean.childClass();
        // 分析结果集字段信息
        List<Object> childList = new ArrayList<>();
        List<T> targetList = new ArrayList<>();
        metaData = resultSet.getMetaData();
        while (resultSet.next()) {
            childList.add(processResultRow(resultSet));
        }
        Map<Object, List<Object>> childMap = ListUtils.groupBy(childList, row -> {
            JsonWrapper jsonWrapper = JsonWrapper.toJson(row);
            return jsonWrapper.getAsJsonObject().getString(childBean.groupName());
        });
        new MapUtils<Object, List<Object>>().foreach(childMap, (key, value) -> {
            try {
                ClassUtils.BeanWrapper<T> targetWrapper = ClassUtils.wrapper(beanClass.newInstance());
                targetWrapper.setValue(childBean.groupName(), key);
                targetWrapper.setValue(childBean.childListName(), value);
                targetList.add(targetWrapper.getTargetObject());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return MapUtils.WAY.NONE;
        });
        return targetList;
    }

    protected Object processResultRow(ResultSet resultSet) throws Exception {
        ClassUtils.BeanWrapper<?> childWrapper = ClassUtils.wrapper(childClass.newInstance());
        for (int idx = 0; idx < metaData.getColumnCount(); idx++) {
            Object value = resultSet.getObject(idx + 1);
            if (value != null) {
                childWrapper.setValue(StringUtils.uncapitalize(EntityMeta.propertyNameToFieldName(metaData.getColumnLabel(idx + 1))), value);
            }
        }
        return childWrapper.getTargetObject();
    }
}
