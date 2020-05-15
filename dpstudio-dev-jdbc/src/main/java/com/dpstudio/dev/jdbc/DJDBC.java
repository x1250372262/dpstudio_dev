package com.dpstudio.dev.jdbc;

import com.dpstudio.dev.jdbc.session.IDSession;
import com.dpstudio.dev.jdbc.session.impl.DSessionImpl;
import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.IResultSet;
import net.ymate.platform.persistence.IShardingable;
import net.ymate.platform.persistence.Page;
import net.ymate.platform.persistence.base.IEntity;
import net.ymate.platform.persistence.jdbc.ISession;
import net.ymate.platform.persistence.jdbc.base.IResultSetHandler;
import net.ymate.platform.persistence.jdbc.query.BatchSQL;
import net.ymate.platform.persistence.jdbc.query.EntitySQL;
import net.ymate.platform.persistence.jdbc.query.SQL;
import net.ymate.platform.persistence.jdbc.query.Where;
import net.ymate.platform.persistence.jdbc.support.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/5/11.
 * @Time: 11:20 上午.
 * @Description:
 */
public class DJDBC {


    private static ISession iSession;

    private IDSession idSession = new DSessionImpl();

    public static DJDBC get() {
        return new DJDBC();
    }

    public static DJDBC get(String dsName) {
        return new DJDBC(dsName);
    }

    public ISession session() {
        return iSession;
    }

    private DJDBC() {
        try {
            if (iSession == null) {
                iSession = idSession.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("jdbc初始化失败" + e.getMessage());
        }
    }

    private DJDBC(String dsName) {
        try {
            if (iSession == null) {
                iSession = idSession.get(dsName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("jdbc初始化失败" + e.getMessage());
        }
    }

    public <T> IResultSet<T> find(SQL sql, IResultSetHandler<T> handler) throws Exception {
        IResultSet<T> returnValue = iSession.find(sql, handler);
        iSession.close();
        return returnValue;
    }

    public <T> IResultSet<T> find(SQL sql, IResultSetHandler<T> handler, Page page) throws Exception {
        IResultSet<T> returnValue = iSession.find(sql, handler, page);
        iSession.close();
        return returnValue;
    }

    public <T extends IEntity> IResultSet<T> find(T entity) throws Exception {
        return find(entity, Fields.create(), null, entity instanceof IShardingable ? (IShardingable) entity : null);
    }


    public <T extends IEntity> IResultSet<T> find(T entity, IShardingable shardingable) throws Exception {
        return find(entity, Fields.create(), null, shardingable);
    }


    public <T extends IEntity> IResultSet<T> find(T entity, Page page) throws Exception {
        return find(entity, Fields.create(), page, entity instanceof IShardingable ? (IShardingable) entity : null);
    }


    public <T extends IEntity> IResultSet<T> find(T entity, Page page, IShardingable shardingable) throws Exception {
        return find(entity, Fields.create(), page, shardingable);
    }


    public <T extends IEntity> IResultSet<T> find(T entity, Fields filter) throws Exception {
        return find(entity, filter, null, entity instanceof IShardingable ? (IShardingable) entity : null);
    }


    public <T extends IEntity> IResultSet<T> find(T entity, Fields filter, IShardingable shardingable) throws Exception {
        return find(entity, filter, null, shardingable);
    }


    public <T extends IEntity> IResultSet<T> find(T entity, Fields filter, Page page) throws Exception {
        return find(entity, filter, page, entity instanceof IShardingable ? (IShardingable) entity : null);
    }


    @SuppressWarnings("unchecked")
    public <T extends IEntity> IResultSet<T> find(T entity, Fields filter, Page page, IShardingable shardingable) throws Exception {
        return (IResultSet<T>) this.find(EntitySQL.create(entity.getClass()).field(filter), Where.create(BaseEntity.buildEntityCond(entity)), page, shardingable);
    }


    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity) throws Exception {
        return find(entity, null, null, null);
    }


    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, IShardingable shardingable) throws Exception {
        return this.find(entity, null, null, shardingable);
    }


    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, Page page) throws Exception {
        return find(entity, null, page, null);
    }


    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, Page page, IShardingable shardingable) throws Exception {
        return this.find(entity, null, page, shardingable);
    }


    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, Where where) throws Exception {
        return find(entity, where, null, null);
    }


    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, Where where, IShardingable shardingable) throws Exception {
        return this.find(entity, where, null, shardingable);
    }


    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, Where where, Page page) throws Exception {
        return find(entity, where, page, null);
    }


    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, Where where, Page page, IShardingable shardingable) throws Exception {
        IResultSet<T> returnValue = iSession.find(entity, where, page, shardingable);
        iSession.close();
        return returnValue;
    }


    public <T extends IEntity> T find(EntitySQL<T> entity, Serializable id) throws Exception {
        return find(entity, id, null);
    }


    public <T extends IEntity> T find(EntitySQL<T> entity, Serializable id, IShardingable shardingable) throws Exception {
        T returnValue = iSession.find(entity, id, shardingable);
        iSession.close();
        return returnValue;
    }


    public <T> T findFirst(SQL sql, IResultSetHandler<T> handler) throws Exception {
        T returnValue = iSession.findFirst(sql, handler);
        iSession.close();
        return returnValue;
    }


    public <T extends IEntity> T findFirst(EntitySQL<T> entity) throws Exception {
        return findFirst(entity, null, null);
    }


    public <T extends IEntity> T findFirst(EntitySQL<T> entity, IShardingable shardingable) throws Exception {
        return findFirst(entity, null, shardingable);
    }


    public <T extends IEntity> T findFirst(EntitySQL<T> entity, Where where) throws Exception {
        return findFirst(entity, where, null);
    }


    public <T extends IEntity> T findFirst(EntitySQL<T> entity, Where where, IShardingable shardingable) throws Exception {
        T returnValue = iSession.findFirst(entity, where, shardingable);
        iSession.close();
        return returnValue;
    }


    public int executeForUpdate(SQL sql) throws Exception {
        int returnValue = iSession.executeForUpdate(sql);
        iSession.close();
        return returnValue;
    }


    public int[] executeForUpdate(BatchSQL sql) throws Exception {
        int[] returnValue = iSession.executeForUpdate(sql);
        iSession.close();
        return returnValue;
    }


    public <T extends IEntity> T update(T entity, Fields filter) throws Exception {
        return update(entity, filter, entity instanceof IShardingable ? (IShardingable) entity : null);
    }


    public <T extends IEntity> T update(T entity, Fields filter, IShardingable shardingable) throws Exception {
        T returnValue = iSession.update(entity, filter, shardingable);
        iSession.close();
        return returnValue;
    }


    public <T extends IEntity> List<T> update(List<T> entities, Fields filter) throws Exception {
        List<T> returnValue = iSession.update(entities, filter);
        iSession.close();
        return returnValue;
    }


    public <T extends IEntity> T insert(T entity) throws Exception {
        return insert(entity, null, (entity instanceof IShardingable ? (IShardingable) entity : null));
    }


    public <T extends IEntity> T insert(T entity, IShardingable shardingable) throws Exception {
        return insert(entity, null, shardingable);
    }


    public <T extends IEntity> T insert(T entity, Fields filter) throws Exception {
        return insert(entity, filter, (entity instanceof IShardingable ? (IShardingable) entity : null));
    }


    public <T extends IEntity> T insert(T entity, Fields filter, IShardingable shardingable) throws Exception {
        T returnValue = iSession.insert(entity, filter, shardingable);
        iSession.close();
        return returnValue;
    }


    public <T extends IEntity> List<T> insert(List<T> entities) throws Exception {
        List<T> returnValue = insert(entities, null);
        return returnValue;
    }

    @SuppressWarnings("unchecked")
    public <T extends IEntity> List<T> insert(List<T> entities, Fields filter) throws Exception {
        List<T> returnValue = iSession.insert(entities, filter);
        iSession.close();
        return returnValue;
    }

    public int insertAll(List<?> entities) throws Exception {
        int result = idSession.insertAll(entities);
        iSession.close();
        return result;
    }

    public <T extends IEntity> int delete(Class<T> entityClass, Serializable id) throws Exception {
        return delete(entityClass, id, null);
    }


    public <T extends IEntity> int delete(Class<T> entityClass, Serializable id, IShardingable shardingable) throws Exception {
        int returnValue = iSession.delete(entityClass, id, shardingable);
        iSession.close();
        return returnValue;
    }


    public <T extends IEntity> List<T> delete(List<T> entities) throws Exception {
        List<T> returnValue = iSession.delete(entities);
        iSession.close();
        return returnValue;
    }


    public <T extends IEntity> int[] delete(Class<T> entityClass, Serializable[] ids) throws Exception {
        int[] returnValue = iSession.delete(entityClass, ids);
        iSession.close();
        return returnValue;
    }


    public <T extends IEntity> long count(Class<T> entityClass, Where where) throws Exception {
        return count(entityClass, where, null);
    }


    public <T extends IEntity> long count(Class<T> entityClass) throws Exception {
        return count(entityClass, null, null);
    }


    public <T extends IEntity> long count(Class<T> entityClass, Where where, IShardingable shardingable) throws Exception {
        long returnValue = iSession.count(entityClass, where, shardingable);
        iSession.close();
        return returnValue;
    }


    public long count(SQL sql) throws Exception {
        long returnValue = iSession.count(sql);
        iSession.close();
        return returnValue;
    }
}
