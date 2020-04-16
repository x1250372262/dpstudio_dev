package com.dpstudio.dev.jdbc;

import com.dpstudio.dev.jdbc.bean.Params;
import net.ymate.platform.persistence.IResultSet;
import net.ymate.platform.persistence.Page;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.base.impl.BeanResultSetHandler;
import net.ymate.platform.persistence.jdbc.query.SQL;
import net.ymate.platform.persistence.jdbc.query.Select;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/10.
 * @Time: 3:14 下午.
 * @Description:
 */
public class SqlHelper<T> {

    private final Class<?> cls;

    public SqlHelper(Class<?> cls) {
        this.cls = cls;
    }

    @SuppressWarnings("unchecked")
    public <T> IResultSet<T> find(Params params, Page page) throws Exception {
        return JDBC.get().openSession(session -> {
            String prefix = session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix();
            Select select = SelectHelper.init(cls, prefix).create(params);
            if (page != null) {
                return session.find(SQL.create(select), new BeanResultSetHandler<T>((Class<T>) cls), page);
            }
            return session.find(SQL.create(select), new BeanResultSetHandler<T>((Class<T>) cls));

        });
    }

    @SuppressWarnings("unchecked")
    public IResultSet<T> find(Params params) throws Exception {
        return JDBC.get().openSession(session -> {
            String prefix = session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix();
            Select select = SelectHelper.init(cls, prefix).create(params);
            return session.find(SQL.create(select), new BeanResultSetHandler<T>((Class<T>) cls));

        });
    }

    @SuppressWarnings("unchecked")
    public IResultSet<T> find(Page page) throws Exception {
        return JDBC.get().openSession(session -> {
            String prefix = session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix();
            Select select = SelectHelper.init(cls, prefix).create();
            if (page != null) {
                return session.find(SQL.create(select), new BeanResultSetHandler<T>((Class<T>) cls), page);
            }
            return session.find(SQL.create(select), new BeanResultSetHandler<T>((Class<T>) cls));

        });
    }

    @SuppressWarnings("unchecked")
    public IResultSet<T> find() throws Exception {
        return JDBC.get().openSession(session -> {
            String prefix = session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix();
            Select select = SelectHelper.init(cls, prefix).create();
            return session.find(SQL.create(select), new BeanResultSetHandler<T>((Class<T>) cls));

        });
    }


    @SuppressWarnings("unchecked")
    public T findFirst() throws Exception {
        return JDBC.get().openSession(session -> {
            String prefix = session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix();
            Select select = SelectHelper.init(cls, prefix).create();
            return session.findFirst(SQL.create(select), new BeanResultSetHandler<T>((Class<T>) cls));

        });
    }


    @SuppressWarnings("unchecked")
    public T findFirst(Params params) throws Exception {
        return JDBC.get().openSession(session -> {
            String prefix = session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix();
            Select select = SelectHelper.init(cls, prefix).create(params);
            return session.findFirst(SQL.create(select), new BeanResultSetHandler<T>((Class<T>) cls));

        });
    }

}
