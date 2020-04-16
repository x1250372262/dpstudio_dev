package com.dpstudio.dev.jdbc;

import com.dpstudio.dev.jdbc.annotation.*;
import com.dpstudio.dev.jdbc.bean.Params;
import com.dpstudio.dev.jdbc.exception.SqlException;
import com.dpstudio.dev.utils.ObjectUtils;
import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.jdbc.query.Cond;
import net.ymate.platform.persistence.jdbc.query.Select;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/10.
 * @Time: 3:14 下午.
 * @Description:
 */
public class SelectHelper {

    private String prefix;

    private Class cls;

    private SelectHelper(Class cls, String prefix) {
        this.prefix = prefix;
        this.cls = cls;
    }


    public static SelectHelper init(Class cls, String prefix) {
        return new SelectHelper(cls, prefix);
    }

    /**
     * 创建字段信息
     *
     * @param
     * @return
     */
    private Fields createFields() {
        java.lang.reflect.Field[] fieldList = cls.getDeclaredFields();
        Fields fields = Fields.create();
        for (java.lang.reflect.Field field : fieldList) {
            Field fieldInfo = field.getAnnotation(Field.class);
            if (fieldInfo != null) {
                String dbFiled = StringUtils.defaultIfBlank(fieldInfo.dbField(), field.getName());
                if (StringUtils.isNotBlank(fieldInfo.filed())) {
                    fields.add(fieldInfo.filed());
                } else {
                    if (StringUtils.isNotBlank(fieldInfo.alias())) {
                        fields.add(fieldInfo.tableAlias(), dbFiled, fieldInfo.alias());
                    } else {
                        fields.add(fieldInfo.tableAlias(), dbFiled);
                    }
                }
            }
        }
        return fields;
    }

    /**
     * 创建连接信息
     *
     * @param
     * @return
     */
    private Select createJoin(Select select, Query query) {
        if (query.joins().length > 0) {
            List<Join> joinList = Arrays.asList(query.joins());
            for (Join join : joinList) {
                net.ymate.platform.persistence.jdbc.query.Join ympJoin = null;
                if (Join.JoinWay.LEFT.equals(join.joinWay())) {
                    ympJoin = net.ymate.platform.persistence.jdbc.query.Join.left(prefix, join.tableName()).alias(join.alias());
                } else if (Join.JoinWay.RIGHT.equals(join.joinWay())) {
                    ympJoin = net.ymate.platform.persistence.jdbc.query.Join.right(prefix, join.tableName()).alias(join.alias());
                } else if (Join.JoinWay.INNER.equals(join.joinWay())) {
                    ympJoin = net.ymate.platform.persistence.jdbc.query.Join.inner(prefix, join.tableName());
                }
                if (ympJoin != null) {
                    ympJoin.alias(join.alias())
                            .on(Cond.create().opt(join.alias(), join.field(), Cond.OPT.EQ, join.JoinAlias(), join.JoinField()));
                    select.join(ympJoin);
                }
            }
        }
        return select;
    }

    /**
     * 获取cond
     * @param cond
     * @param params
     * @return
     */
    private Cond getCond(com.dpstudio.dev.jdbc.annotation.Cond cond, Params params) {
        Cond tempCond = null;
        String paramName = StringUtils.defaultIfBlank(cond.paramName(), cond.dbFiled());
        Object paramValue = params.attr(paramName);
        if (ObjectUtils.isEmpty(paramValue)) {
            return tempCond;
        }
        switch (cond.opt()) {
            case EQ:
                if (StringUtils.isNotBlank(cond.tableAlias())) {
                    tempCond = Cond.create().and().eq(cond.tableAlias(), cond.dbFiled()).param(paramValue);
                } else {
                    tempCond = Cond.create().and().eq(cond.tableAlias(), cond.dbFiled()).param(paramValue);
                }
                break;
            case GT:
                if (StringUtils.isNotBlank(cond.tableAlias())) {
                    tempCond = Cond.create().and().gt(cond.tableAlias(), cond.dbFiled()).param(paramValue);
                } else {
                    tempCond = Cond.create().and().gt(cond.tableAlias(), cond.dbFiled()).param(paramValue);
                }
                break;
            case LT:
                if (StringUtils.isNotBlank(cond.tableAlias())) {
                    tempCond = Cond.create().and().lt(cond.tableAlias(), cond.dbFiled()).param(paramValue);
                } else {
                    tempCond = Cond.create().and().lt(cond.tableAlias(), cond.dbFiled()).param(paramValue);
                }
                break;
            case LIKE:
                if (StringUtils.isNotBlank(cond.tableAlias())) {
                    tempCond = Cond.create().and().like(cond.tableAlias(), cond.dbFiled()).param("%" + paramValue + "%");
                } else {
                    tempCond = Cond.create().and().like(cond.tableAlias(), cond.dbFiled()).param("%" + paramValue + "%");
                }
                break;
            case GT_EQ:
                if (StringUtils.isNotBlank(cond.tableAlias())) {
                    tempCond = Cond.create().and().gtEq(cond.tableAlias(), cond.dbFiled()).param(paramValue);
                } else {
                    tempCond = Cond.create().and().gtEq(cond.tableAlias(), cond.dbFiled()).param(paramValue);
                }
                break;
            case LT_EQ:
                if (StringUtils.isNotBlank(cond.tableAlias())) {
                    tempCond = Cond.create().and().ltEq(cond.tableAlias(), cond.dbFiled()).param(paramValue);
                } else {
                    tempCond = Cond.create().and().ltEq(cond.tableAlias(), cond.dbFiled()).param(paramValue);
                }
                break;
            case NOT_EQ:
                if (StringUtils.isNotBlank(cond.tableAlias())) {
                    tempCond = Cond.create().and().notEq(cond.tableAlias(), cond.dbFiled()).param(paramValue);
                } else {
                    tempCond = Cond.create().and().notEq(cond.tableAlias(), cond.dbFiled()).param(paramValue);
                }
                break;
            default:
                break;
        }
        return tempCond;
    }

    /**
     * 创建where
     * @param params
     * @return
     */
    private net.ymate.platform.persistence.jdbc.query.Where createWhere(Params params) {
        net.ymate.platform.persistence.jdbc.query.Where where = null;
        Where wheres = (Where) cls.getAnnotation(Where.class);
        if (wheres == null) {
            return where;
        }

        if (wheres.conds().length > 0) {
            Cond condAll = Cond.create().eqOne();
            List<com.dpstudio.dev.jdbc.annotation.Cond> condList = Arrays.asList(wheres.conds());
            for (com.dpstudio.dev.jdbc.annotation.Cond cond : condList) {
                Cond tempCond = getCond(cond, params);
                if (tempCond != null) {
                    condAll.cond(tempCond);
                }
            }
            where = net.ymate.platform.persistence.jdbc.query.Where.create(condAll);
        }

        if (wheres.orderBys().length > 0) {
            if (where == null) {
                where = net.ymate.platform.persistence.jdbc.query.Where.create();
            }
            List<OrderBy> orderByList = Arrays.asList(wheres.orderBys());
            for (OrderBy order : orderByList) {
                if (OrderBy.TYPE.DESC.equals(order.type())) {
                    where.orderDesc(order.tableAlias(), order.dbFiled());
                } else {
                    where.orderAsc(order.tableAlias(), order.dbFiled());
                }
            }
        }

        return where;
    }

    public Select create(Params params) throws SqlException {
        Query query = (Query) cls.getAnnotation(Query.class);
        if (query == null) {
            throw new SqlException("vo对象不包含Query注解");
        }
        Select select = Select.create(prefix, query.tableName(), query.alias());
        select.field(createFields());
        select = createJoin(select, query);
        net.ymate.platform.persistence.jdbc.query.Where where = createWhere(params);
        if (where != null) {
            select.where(where);
        }
        return select;
    }

    public Select create() throws SqlException {
        Query query = (Query) cls.getAnnotation(Query.class);
        if (query == null) {
            throw new SqlException("vo对象不包含Query注解");
        }
        Select select = Select.create(prefix, query.tableName(), query.alias());
        select.field(createFields());
        select = createJoin(select, query);
        net.ymate.platform.persistence.jdbc.query.Where where = createWhere(null);
        if (where != null) {
            select.where(where);
        }
        return select;
    }
}
