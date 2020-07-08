package com.dpstudio.dev.doc.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 接口业务模块
 */
public class ApiModule {

    /**
     * 源码在哪个类
     */
    @JsonIgnore
    private transient Class<?> type;

    /**
     * 业务模块的描述
     */
    private String comment;

    /**
     * 此业务模块下有哪些接口
     */
    private List<ApiAction> apiActions = new LinkedList<>();

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ApiAction> getApiActions() {
        return apiActions;
    }

    public void setApiActions(List<ApiAction> apiActions) {
        this.apiActions = apiActions;
    }
}
