package com.mx.dev.support.log.impl;

import com.mx.dev.support.UserSession;
import com.mx.dev.support.log.ILogHandler;
import com.mx.dev.support.log.LR;
import com.mx.dev.support.log.annotation.Log;
import com.mx.dev.support.log.annotation.LogGroup;
import net.ymate.platform.commons.lang.BlurObject;
import net.ymate.platform.commons.util.DateTimeUtils;
import net.ymate.platform.commons.util.UUIDUtils;
import net.ymate.platform.persistence.jdbc.IDatabaseSessionExecutor;
import net.ymate.platform.persistence.jdbc.JDBC;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mengxiang.
 * @Date: 2020/5/26.
 * @Time: 9:20 上午.
 * @Description:
 */
public class DefaultLogHandler implements ILogHandler {

    private String createContent(String moduleName, String action, String resourceId, String createUser) {
        return "模块名:" + moduleName + "," +
                createUser +
                "在" + DateTimeUtils.formatTime(DateTimeUtils.currentTimeMillis(), DateTimeUtils.YYYY_MM_DD_HH_MM_SS) +
                action +
                "了一条id为" +
                resourceId +
                "的数据";
    }

    @Override
    public void create(LogGroup logGroup, LR lr) throws Exception {
        List<com.mx.dev.support.log.model.Log> logList = new ArrayList<>();
        String createUser = UserSession.current() != null ? UserSession.current().getUid() : "default";
        Log[] logs = logGroup.logs();
        for (Log log : logs) {
            String resourceId = BlurObject.bind(lr.get(log.logKey()).get("id")).toStringValue();
            com.mx.dev.support.log.model.Log logBean = com.mx.dev.support.log.model.Log.builder()
                    .id(UUIDUtils.UUID())
                    .moduleType(log.moduleType())
                    .moduleName(log.moduleName())
                    .action(log.action())
                    .type(log.type())
                    .resourceId(resourceId)
                    .createTime(DateTimeUtils.currentTimeMillis())
                    .createUser(createUser)
                    .content(createContent(log.moduleName(), log.action(), resourceId, createUser))
                    .build();
            logList.add(logBean);
        }
        if (!logList.isEmpty()) {
            JDBC.get().openSession((IDatabaseSessionExecutor<Object>) session -> session.insert(logList));
        }

    }
}
