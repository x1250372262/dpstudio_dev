package com.dpstudio.dev.bug.service.impl;

import com.dpstudio.dev.bug.dao.IBugDao;
import com.dpstudio.dev.bug.dao.IBugLogDao;
import com.dpstudio.dev.bug.model.Bug;
import com.dpstudio.dev.bug.model.BugLog;
import com.dpstudio.dev.bug.service.IBugService;
import com.dpstudio.dev.bug.vo.BugOpVO;
import com.dpstudio.dev.bug.vo.BugQueryVO;
import com.dpstudio.dev.core.R;
import com.dpstudio.dev.support.UserSession;
import com.dpstudio.dev.code.C;
import com.dpstudio.dev.utils.BeanUtils;
import net.ymate.platform.commons.util.DateTimeUtils;
import net.ymate.platform.commons.util.UUIDUtils;
import net.ymate.platform.core.beans.annotation.Bean;
import net.ymate.platform.core.beans.annotation.Inject;
import net.ymate.platform.core.persistence.IResultSet;
import net.ymate.platform.core.persistence.annotation.Transaction;

import java.util.Objects;

/**
 * @Author: mengxiang.
 * @Date: 2020/7/6.
 * @Time: 下午3:14.
 * @Description:
 */
@Transaction
@Bean
public class BugServiceImpl implements IBugService {

    @Inject
    private IBugDao iBugDao;
    @Inject
    private IBugLogDao iBugLogDao;

    @Transaction
    @Override
    public R create(BugOpVO bugOpVO) throws Exception {
        Bug bug = BeanUtils.copy(bugOpVO, Bug::new);
        bug.setId(UUIDUtils.UUID());
        bug.setCreateTime(DateTimeUtils.currentTimeMillis());
        bug.setCreateUser(UserSession.current().getUid());
        bug.setLastModifyTime(DateTimeUtils.currentTimeMillis());
        bug.setLastModifyUser(UserSession.current().getUid());
        bug.setStatus(0);
        bug = iBugDao.create(bug);

        UserSession userSession = UserSession.current();
        String handlerUser = Objects.isNull(userSession.getAttribute("name")) ? userSession.getUid() : userSession.getAttribute("name");
        BugLog bugLog = BugLog.builder()
                .id(UUIDUtils.UUID())
                .bugId(bug.getId())
                .action("添加")
                .handlerTime(DateTimeUtils.currentTimeMillis())
                .handlerUser(handlerUser)
                .build();
        iBugLogDao.create(bugLog);
        return R.ok();
    }

    @Override
    public R update(String id, BugOpVO bugOpVO) throws Exception {

        UserSession userSession = UserSession.current();
        String handlerUser = Objects.isNull(userSession.getAttribute("name")) ? userSession.getUid() : userSession.getAttribute("name");
        Bug bug = iBugDao.findById(id);
        if (bug != null) {
            if (Objects.equals(bug.getStatus(), 1)) {
                bug.setStatus(0);
            }
            bug = BeanUtils.copy(bugOpVO, Bug::new);
            bug.setLastModifyTime(DateTimeUtils.currentTimeMillis());
            bug.setLastModifyUser(userSession.getUid());
            bug = iBugDao.update(bug, Bug.FIELDS.LEVEL, Bug.FIELDS.STATUS, Bug.FIELDS.TITLE, Bug.FIELDS.CONTENT,
                    Bug.FIELDS.TYPE, Bug.FIELDS.LAST_MODIFY_TIME, Bug.FIELDS.LAST_MODIFY_USER);

            BugLog bugLog = BugLog.builder()
                    .id(UUIDUtils.UUID())
                    .bugId(bug.getId())
                    .action("修改")
                    .handlerTime(DateTimeUtils.currentTimeMillis())
                    .handlerUser(handlerUser)
                    .build();
            iBugLogDao.create(bugLog);
        } else {
            bug = BeanUtils.copy(bugOpVO, Bug::new);
            bug.setId(UUIDUtils.UUID());
            bug.setCreateTime(DateTimeUtils.currentTimeMillis());
            bug.setCreateUser(UserSession.current().getUid());
            bug.setLastModifyTime(DateTimeUtils.currentTimeMillis());
            bug.setLastModifyUser(UserSession.current().getUid());
            bug.setStatus(0);
            bug = iBugDao.create(bug);

            BugLog bugLog = BugLog.builder()
                    .id(UUIDUtils.UUID())
                    .bugId(bug.getId())
                    .action("添加")
                    .handlerTime(DateTimeUtils.currentTimeMillis())
                    .handlerUser(handlerUser)
                    .build();
            iBugLogDao.create(bugLog);
        }
        return R.ok();
    }

    @Override
    public Bug detail(String id) throws Exception {
        return iBugDao.findById(id);
    }

    @Override
    @Transaction
    public R updateStatus(String id, Integer status) throws Exception {

        UserSession userSession = UserSession.current();
        String handlerUser = Objects.isNull(userSession.getAttribute("name")) ? userSession.getUid() : userSession.getAttribute("name");
        Bug bug = iBugDao.findById(id);
        if (bug != null) {
            bug.setStatus(status);
            bug.setHandlerUser(userSession.getUid());
            bug.setHandlerTime(DateTimeUtils.currentTimeMillis());
            bug.setLastModifyTime(DateTimeUtils.currentTimeMillis());
            bug.setLastModifyUser(userSession.getUid());
            bug = iBugDao.update(bug, Bug.FIELDS.STATUS, Bug.FIELDS.LAST_MODIFY_TIME, Bug.FIELDS.LAST_MODIFY_USER, Bug.FIELDS.HANDLER_TIME, Bug.FIELDS.HANDLER_USER);


            BugLog bugLog = BugLog.builder()
                    .id(UUIDUtils.UUID())
                    .bugId(bug.getId())
                    .action("确认")
                    .handlerTime(DateTimeUtils.currentTimeMillis())
                    .handlerUser(handlerUser)
                    .build();
            iBugLogDao.create(bugLog);
            return R.ok();
        }
        return R.create(C.NO_DATA.getCode()).msg(C.NO_DATA.getMsg());
    }

    @Override
    public IResultSet<BugQueryVO> list(String createUser, String handlerUser, String title, Integer status, Integer type, Integer level, int page, int pageSize) throws Exception {
        return iBugDao.findAll(createUser, handlerUser, title, status, type, level, page, pageSize);
    }
}
