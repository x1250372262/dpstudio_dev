package com.dpstudio.dev.bug.dao.impl;

import com.dpstudio.dev.bug.dao.IBugDao;
import com.dpstudio.dev.bug.model.Bug;
import com.dpstudio.dev.bug.model.BugUser;
import com.dpstudio.dev.bug.vo.BugQueryVO;
import net.ymate.platform.core.beans.annotation.Bean;
import net.ymate.platform.core.persistence.Fields;
import net.ymate.platform.core.persistence.IResultSet;
import net.ymate.platform.core.persistence.Page;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.base.impl.BeanResultSetHandler;
import net.ymate.platform.persistence.jdbc.query.*;

/**
 * @Author: xujianpeng.
 * @Date: 2020/7/6.
 * @Time: 下午3:10.
 * @Description:
 */
@Bean
public class BugDaoImpl implements IBugDao {

    @Override
    public Bug create(Bug bug) throws Exception {
        return bug.save();
    }

    @Override
    public Bug update(Bug bug, String... fields) throws Exception {
        return bug.update(Fields.create(fields));
    }

    @Override
    public Bug findById(String id, String... fields) throws Exception {
        return Bug.builder().id(id).build().load(Fields.create(fields));
    }

    @Override
    public IResultSet<BugQueryVO> findAll(String createUser, String handlerUser, String title, Integer status, Integer type, Integer level, int page, int pageSize) throws Exception {
        return JDBC.get().openSession(session -> {
            Cond cond = Cond.create().eqOne();
            cond.exprNotEmpty(createUser, c -> c.and().eq(Fields.field("b", Bug.FIELDS.CREATE_USER)).param(createUser));
            cond.exprNotEmpty(handlerUser, c -> c.and().eq(Fields.field("b", Bug.FIELDS.CREATE_USER)).param(handlerUser));
            cond.exprNotEmpty(title, c -> c.and().eq(Fields.field("b", Bug.FIELDS.CREATE_USER)).param("%" + title + "%"));
            cond.exprNotEmpty(status, c -> c.and().eq(Fields.field("b", Bug.FIELDS.CREATE_USER)).param(status));
            cond.exprNotEmpty(type, c -> c.and().eq(Fields.field("b", Bug.FIELDS.CREATE_USER)).param(type));
            cond.exprNotEmpty(level, c -> c.and().eq(Fields.field("b", Bug.FIELDS.CREATE_USER)).param(level));

            String prefix = session.getConnectionHolder().getDataSourceConfig().getTablePrefix();
            Join createJoin = Join.left(prefix, BugUser.TABLE_NAME).alias("bu")
                    .on(Cond.create().eq(Fields.field("bu", BugUser.FIELDS.ID), Fields.field("b", Bug.FIELDS.CREATE_USER)));
            Join handlerJoin = Join.left(prefix, BugUser.TABLE_NAME).alias("bu1")
                    .on(Cond.create().eq(Fields.field("bu1", BugUser.FIELDS.ID), Fields.field("b", Bug.FIELDS.HANDLER_USER)));
            Join modifyJoin = Join.left(prefix, BugUser.TABLE_NAME).alias("bu2")
                    .on(Cond.create().eq(Fields.field("bu2", BugUser.FIELDS.ID), Fields.field("b", Bug.FIELDS.LAST_MODIFY_USER)));
            Select select = Select.create(prefix, Bug.TABLE_NAME, "b")
                    .join(createJoin).join(handlerJoin).join(modifyJoin)
                    .field("b", Bug.FIELDS.ID)
                    .field("b", Bug.FIELDS.TITLE)
                    .field("b", Bug.FIELDS.TYPE)
                    .field("b", Bug.FIELDS.CONTENT)
                    .field("b", Bug.FIELDS.HANDLER_TIME)
                    .field("b", Bug.FIELDS.CREATE_TIME)
                    .field("b", Bug.FIELDS.STATUS)
                    .field("b", Bug.FIELDS.LEVEL)
                    .field("b", Bug.FIELDS.LAST_MODIFY_TIME)
                    .field("bu", BugUser.FIELDS.NAME, Bug.FIELDS.CREATE_USER)
                    .field("bu1", BugUser.FIELDS.NAME, Bug.FIELDS.HANDLER_USER)
                    .field("bu2", BugUser.FIELDS.NAME, Bug.FIELDS.LAST_MODIFY_USER)
                    .where(Where.create(cond).orderByDesc("b", Bug.FIELDS.LAST_MODIFY_TIME));
            return session.find(SQL.create(select), new BeanResultSetHandler<>(BugQueryVO.class), Page.create(page).pageSize(pageSize));
        });
    }
}

