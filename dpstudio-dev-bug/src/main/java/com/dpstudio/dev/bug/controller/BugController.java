package com.dpstudio.dev.bug.controller;

import com.dpstudio.dev.bug.interCeptor.SessionCheckInterceptor;
import com.dpstudio.dev.bug.model.Bug;
import com.dpstudio.dev.bug.model.BugLog;
import com.dpstudio.dev.bug.model.BugUser;
import com.dpstudio.dev.core.CommonResult;
import net.ymate.framework.webmvc.support.UserSessionBean;
import net.ymate.platform.core.beans.annotation.Before;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.DateTimeUtils;
import net.ymate.platform.core.util.UUIDUtils;
import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.IResultSet;
import net.ymate.platform.persistence.Page;
import net.ymate.platform.persistence.jdbc.ISession;
import net.ymate.platform.persistence.jdbc.ISessionExecutor;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.annotation.Transaction;
import net.ymate.platform.persistence.jdbc.base.impl.MapResultSetHandler;
import net.ymate.platform.persistence.jdbc.query.*;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;

import java.util.Map;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/27.
 * @Time: 10:42 上午.
 * @Description:
 */
@Controller
@RequestMapping("/dpstudio/bug/")
@Before(SessionCheckInterceptor.class)
@Transaction
public class BugController {

    @RequestMapping(value = "/list/")
    public IView login(@RequestParam String createUser,
                       @RequestParam String handlerUser,
                       @RequestParam String title,
                       @RequestParam Integer status,
                       @RequestParam Integer type,
                       @RequestParam Integer level,
                       @RequestParam(defaultValue = "1") final int page,
                       @RequestParam(defaultValue = "10") final int pageSize) throws Exception {

        final Cond cond = Cond.create().eqOne();
        cond.exprNotEmpty(createUser, Cond.create().and().eq("b", Bug.FIELDS.CREATE_USER).param(createUser));
        cond.exprNotEmpty(handlerUser, Cond.create().and().eq("b", Bug.FIELDS.HANDLER_USER).param(handlerUser));
        cond.exprNotEmpty(title, Cond.create().and().like("b", Bug.FIELDS.TITLE).param("%" + title + "%"));
        cond.exprNotEmpty(status, Cond.create().and().eq("b", Bug.FIELDS.STATUS).param(status));
        cond.exprNotEmpty(type, Cond.create().and().eq("b", Bug.FIELDS.TYPE).param(type));
        cond.exprNotEmpty(level, Cond.create().and().eq("b", Bug.FIELDS.LEVEL).param(level));
        IResultSet<Map<String, Object>> resultSet = JDBC.get().openSession(new ISessionExecutor<IResultSet<Map<String, Object>>>() {
            @Override
            public IResultSet<Map<String, Object>> execute(ISession session) throws Exception {
                String prefix = session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix();
                Join createJoin = Join.left(prefix, BugUser.TABLE_NAME).alias("bu")
                        .on(Cond.create().opt("bu", BugUser.FIELDS.ID, Cond.OPT.EQ, "b", Bug.FIELDS.CREATE_USER));
                Join handlerJoin = Join.left(prefix, BugUser.TABLE_NAME).alias("bu1")
                        .on(Cond.create().opt("bu1", BugUser.FIELDS.ID, Cond.OPT.EQ, "b", Bug.FIELDS.HANDLER_USER));
                Join modifyJoin = Join.left(prefix, BugUser.TABLE_NAME).alias("bu2")
                        .on(Cond.create().opt("bu2", BugUser.FIELDS.ID, Cond.OPT.EQ, "b", Bug.FIELDS.LAST_MODIFY_USER));
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
                        .field("b", Bug.FIELDS.HANDLER_USER)
                        .field("b", Bug.FIELDS.CREATE_USER)
                        .field("b", Bug.FIELDS.LAST_MODIFY_TIME)
                        .field("b", Bug.FIELDS.LAST_MODIFY_USER)
                        .field("bu", BugUser.FIELDS.NAME, "cName")
                        .field("bu1", BugUser.FIELDS.NAME, "hName")
                        .field("bu2", BugUser.FIELDS.NAME, "mName")
                        .where(Where.create(cond).orderDesc("b", Bug.FIELDS.LAST_MODIFY_TIME));
                return session.find(SQL.create(select), new MapResultSetHandler(), Page.create(page).pageSize(pageSize));
            }
        });
        return CommonResult.listView(resultSet, page);
    }


    @Transaction
    @RequestMapping(value = "/create/", method = Type.HttpMethod.POST)
    public IView login(@VRequired(msg = "标题不能为空")
                       @RequestParam String title,
                       @VRequired(msg = "请选择类型")
                       @RequestParam Integer type,
                       @VRequired(msg = "请选择优先级")
                       @RequestParam Integer level,
                       @VRequired(msg = "内容不能为空")
                       @RequestParam String content) throws Exception {

        Bug bug = Bug.builder()
                .id(UUIDUtils.UUID())
                .title(title)
                .content(content)
                .type(type)
                .level(level)
                .createTime(DateTimeUtils.currentTimeMillis())
                .status(0)
                .createUser(UserSessionBean.current().getUid())
                .lastModifyUser(UserSessionBean.current().getUid())
                .lastModifyTime(DateTimeUtils.currentTimeMillis())
                .build().save();

        BugLog.builder()
                .id(UUIDUtils.UUID())
                .bugId(bug.getId())
                .action("添加")
                .handlerTime(DateTimeUtils.currentTimeMillis())
                .handlerUser(BlurObject.bind((UserSessionBean.current().getAttribute("name"))).toStringValue())
                .build().save();
        return CommonResult.successView();
    }


    @Transaction
    @RequestMapping(value = "/update/", method = Type.HttpMethod.POST)
    public IView login(@VRequired(msg = "id不能为空")
                       @RequestParam String id,
                       @VRequired(msg = "标题不能为空")
                       @RequestParam String title,
                       @VRequired(msg = "请选择类型")
                       @RequestParam Integer type,
                       @VRequired(msg = "请选择优先级")
                       @RequestParam Integer level,
                       @VRequired(msg = "内容不能为空")
                       @RequestParam String content) throws Exception {

        Bug bug = Bug.builder().id(id).build().load();
        if (bug != null) {
            if (bug.getStatus().intValue() == 1) {
                bug.setStatus(0);
            }
            bug.setTitle(title);
            bug.setContent(content);
            bug.setType(type);
            bug.setLevel(level);
            bug.setLastModifyTime(DateTimeUtils.currentTimeMillis());
            bug.setLastModifyUser(UserSessionBean.current().getUid());
            bug.update(Fields.create(Bug.FIELDS.LEVEL, Bug.FIELDS.STATUS, Bug.FIELDS.TITLE, Bug.FIELDS.CONTENT, Bug.FIELDS.TYPE, Bug.FIELDS.LAST_MODIFY_TIME, Bug.FIELDS.LAST_MODIFY_USER));
            BugLog.builder()
                    .id(UUIDUtils.UUID())
                    .bugId(bug.getId())
                    .action("修改")
                    .handlerTime(DateTimeUtils.currentTimeMillis())
                    .handlerUser(BlurObject.bind((UserSessionBean.current().getAttribute("name"))).toStringValue())
                    .build().save();
        } else {
            Bug.builder()
                    .id(UUIDUtils.UUID())
                    .title(title)
                    .content(content)
                    .type(type)
                    .level(level)
                    .createTime(DateTimeUtils.currentTimeMillis())
                    .status(0)
                    .createUser(UserSessionBean.current().getUid())
                    .lastModifyUser(UserSessionBean.current().getUid())
                    .lastModifyTime(DateTimeUtils.currentTimeMillis())
                    .build().save();

            BugLog.builder()
                    .id(UUIDUtils.UUID())
                    .bugId(bug.getId())
                    .action("添加")
                    .handlerTime(DateTimeUtils.currentTimeMillis())
                    .handlerUser(BlurObject.bind((UserSessionBean.current().getAttribute("name"))).toStringValue())
                    .build().save();
        }

        return CommonResult.successView();
    }


    @RequestMapping(value = "/detail/", method = Type.HttpMethod.GET)
    public IView login(@VRequired(msg = "id不能为空")
                       @RequestParam String id) throws Exception {

        Bug bug = Bug.builder().id(id).build().load();

        return WebResult.succeed().data(bug).toJSON();
    }


    @Transaction
    @RequestMapping(value = "/status/", method = Type.HttpMethod.POST)
    public IView login(@VRequired(msg = "id不能为空")
                       @RequestParam String id,
                       @VRequired(msg = "status不能为空")
                       @RequestParam Integer status) throws Exception {

        Bug bug = Bug.builder().id(id).build().load();
        if (bug != null) {
            bug.setStatus(status);
            bug.setHandlerUser(UserSessionBean.current().getUid());
            bug.setHandlerTime(DateTimeUtils.currentTimeMillis());
            bug.setLastModifyTime(DateTimeUtils.currentTimeMillis());
            bug.setLastModifyUser(UserSessionBean.current().getUid());
            bug.update(Fields.create(Bug.FIELDS.STATUS, Bug.FIELDS.LAST_MODIFY_TIME, Bug.FIELDS.LAST_MODIFY_USER,Bug.FIELDS.HANDLER_TIME,Bug.FIELDS.HANDLER_USER));

            BugLog.builder()
                    .id(UUIDUtils.UUID())
                    .bugId(id)
                    .action("确认")
                    .handlerTime(DateTimeUtils.currentTimeMillis())
                    .handlerUser(BlurObject.bind((UserSessionBean.current().getAttribute("name"))).toStringValue())
                    .build().save();
        }

        return CommonResult.successView();
    }
}
