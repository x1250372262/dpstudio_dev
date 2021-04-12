package com.dpstudio.dev.support.jdbc;

import com.dpstudio.dev.dto.PageDTO;
import net.ymate.platform.core.persistence.Fields;
import net.ymate.platform.core.persistence.IResultSet;
import net.ymate.platform.core.persistence.Page;
import net.ymate.platform.core.persistence.annotation.Property;
import net.ymate.platform.core.persistence.base.IEntity;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.base.impl.BeanResultSetHandler;
import net.ymate.platform.persistence.jdbc.base.impl.EntityResultSetHandler;
import net.ymate.platform.persistence.jdbc.query.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mengxiang.
 * @create: 2021-03-19 14:44
 * @Description: 查询助手
 */
public class QueryHelper {

    private QueryHelper(boolean eqOne) {
        condBuilder = CondBuilder.create(eqOne);
        cond = Cond.create();
        joinList = new ArrayList<>();
        select = Select.create();
        where = Where.create();
    }

    private final CondBuilder condBuilder;

    private final Cond cond;

    private final List<Join> joinList;

    private final Select select;

    private final Where where;

    private Page page;

    private static String prefix;

    static {
        try {
            prefix = JDBC.get().getDefaultConnectionHolder().getDataSourceConfig().getTablePrefix();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static QueryHelper create() {
        return new QueryHelper(false);
    }

    public static QueryHelper eqOne() {
        return new QueryHelper(true);
    }


    public QueryHelper cond(Object condBean) throws Exception {
        cond.cond(condBuilder.build(condBean).cond());
        return this;
    }

    public QueryHelper cond(String prefix, String field, Cond.OPT opt, Object param, boolean exprNotEmpty) throws Exception {
        cond.cond(condBuilder.build(prefix, field, opt, param, exprNotEmpty).cond());
        return this;
    }

    public QueryHelper cond(String field, Cond.OPT opt, Object param, boolean exprNotEmpty) throws Exception {
        cond(null, field, opt, param, exprNotEmpty);
        return this;
    }

    public QueryHelper cond(String field, Cond.OPT opt, Object param) throws Exception {
        cond(field, opt, param, true);
        return this;
    }

    public QueryHelper cond(Cond condition) throws Exception {
        cond.cond(condition);
        return this;
    }

    public QueryHelper join(String prefix, String tableName, String alias, Join.Type type, String filedOnePrefix, String filedOne, String filedTwoPrefix, String filedTwo) throws Exception {
        Join join = null;
        switch (type) {
            case LEFT:
                join = Join.left(prefix, tableName).alias(alias)
                        .on(Cond.create().optWrap(Fields.field(filedOnePrefix, filedOne), Cond.OPT.EQ, Fields.field(filedTwoPrefix, filedTwo)));
                break;
            case INNER:
                join = Join.inner(prefix, tableName).alias(alias)
                        .on(Cond.create().optWrap(Fields.field(filedOnePrefix, filedOne), Cond.OPT.EQ, Fields.field(filedTwoPrefix, filedTwo)));
                break;
            case RIGHT:
                join = Join.right(prefix, tableName).alias(alias)
                        .on(Cond.create().optWrap(Fields.field(filedOnePrefix, filedOne), Cond.OPT.EQ, Fields.field(filedTwoPrefix, filedTwo)));
                break;
            default:
                break;
        }
        if (join != null) {
            joinList.add(join);
        }
        return this;
    }

    public QueryHelper join(String tableName, String alias, Join.Type type, String filedOnePrefix, String filedOne, String filedTwoPrefix, String filedTwo) throws Exception {
        return join(prefix, tableName, alias, type, filedOnePrefix, filedOne, filedTwoPrefix, filedTwo);
    }

    public QueryHelper select(String prefix, String tableName, String alias) {
        select.from(prefix, tableName, alias);
        return this;
    }

    public QueryHelper select(String tableName, String alias) throws Exception {
        return select(prefix, tableName, alias);
    }

    public QueryHelper field(String prefix, String field, String alias) throws Exception {
        select.field(prefix, field, alias);
        return this;
    }

    public QueryHelper field(String prefix, String field, boolean wrapIdentifier) throws Exception {
        select.field(prefix, field);
        return this;
    }

    public QueryHelper field(String prefix, String field) throws Exception {
        select.field(prefix, field);
        return this;
    }

    public QueryHelper field(String field, boolean wrapIdentifier) throws Exception {
        select.field(field, wrapIdentifier);
        return this;
    }

    public QueryHelper field(String prefix, String... fields) throws Exception {
        List<String> fieldList = Fields.create(fields).fields();
        for (String field : fieldList) {
            select.field(prefix, field);
        }
        return this;
    }

    public QueryHelper field(String prefix, Class<?> clazz, String... fields) throws Exception {
        Field[] beanFields = clazz.getDeclaredFields();
        if (fields.length <= 0) {
            return this;
        }
        Fields exFields = Fields.create(fields);
        for (Field field : beanFields) {
            if (!field.isAnnotationPresent(Property.class)) {
                continue;
            }
            Property property = field.getAnnotation(Property.class);
            if (exFields.fields().contains(property.name())) {
                continue;
            }
            field(prefix, property.name());
        }
        return this;
    }

    public QueryHelper orderBy(String prefix, String field, boolean desc) throws Exception {
        if (desc) {
            where.orderByDesc(prefix, field);
        }
        where.orderByAsc(prefix, field);
        return this;
    }

    public QueryHelper page(PageDTO pageDTO) {
        page = pageDTO.toPage();
        return this;
    }


    public <T> IResultSet<T> find(Class<T> clazz) throws Exception {
        for (Join join : joinList) {
            select.join(join);
        }
        select.where(where.cond().cond(cond));
        return JDBC.get().openSession(session -> session.find(SQL.create(select), new BeanResultSetHandler<>(clazz), page));
    }

    public <T extends IEntity> IResultSet<T> findEntity(Class<T> clazz) throws Exception {
        for (Join join : joinList) {
            select.join(join);
        }
        select.where(where.cond().cond(cond));
        return JDBC.get().openSession(session -> session.find(SQL.create(select), new EntityResultSetHandler<>(clazz), page));
    }


    public <T> T findFirst(Class<T> clazz) throws Exception {
        for (Join join : joinList) {
            select.join(join);
        }
        select.where(where.cond().cond(cond));
        return JDBC.get().openSession(session -> session.findFirst(SQL.create(select), new BeanResultSetHandler<>(clazz)));
    }

    public <T extends IEntity> T findFirstEntity(Class<T> clazz) throws Exception {
        for (Join join : joinList) {
            select.join(join);
        }
        select.where(where.cond().cond(cond));
        return JDBC.get().openSession(session -> session.findFirst(SQL.create(select), new EntityResultSetHandler<>(clazz)));
    }


}
