package com.dpstudio.dev.support.log.impl;

import com.dpstudio.dev.support.log.ILogHandler;
import com.dpstudio.dev.support.log.annotation.LogGroup;
import com.dpstudio.dev.support.log.model.Log;
import net.ymate.platform.commons.util.DateTimeUtils;
import net.ymate.platform.commons.util.UUIDUtils;
import net.ymate.platform.persistence.jdbc.IDatabaseSessionExecutor;
import net.ymate.platform.persistence.jdbc.JDBC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/5/26.
 * @Time: 9:20 上午.
 * @Description:
 */
public class DefaultLogHandler implements ILogHandler {

    private String createContent(String moduleName, String action, String resourceId, String createUser) {
        StringBuilder stringBuilder = new StringBuilder("模块名:" + moduleName + ",")
                .append(createUser)
                .append("在" + DateTimeUtils.formatTime(DateTimeUtils.currentTimeMillis(), DateTimeUtils.YYYY_MM_DD_HH_MM_SS))
                .append(action)
                .append("了一条id为")
                .append(resourceId)
                .append("的数据");
        return stringBuilder.toString();
    }

    @Override
    public void create(LogGroup logGroup, Map<String, String> logMap) throws Exception {
        if (logMap.isEmpty()) {
            return;
        }
        List<Log> logList = new ArrayList<>();
        String createUser =  "default";
//        String createUser = UserSessionBean.current() != null ? UserSessionBean.current().getUid() : "default";
        com.dpstudio.dev.support.log.annotation.Log[] logs = logGroup.logs();
        for (com.dpstudio.dev.support.log.annotation.Log log : logs) {
            Log logBean = Log.builder()
                    .id(UUIDUtils.UUID())
                    .moduleType(log.moduleType())
                    .moduleName(log.moduleName())
                    .action(log.action())
                    .type(log.type())
                    .resourceId(logMap.get(log.logKey()))
                    .createTime(DateTimeUtils.currentTimeMillis())
                    .createUser(createUser)
                    .content(createContent(log.moduleName(), log.action(), logMap.get(log.logKey()), createUser))
                    .build();
            logList.add(logBean);
        }
        if (!logList.isEmpty()) {
            JDBC.get().openSession((IDatabaseSessionExecutor<Object>) session -> session.insert(logList));
        }

    }
}
