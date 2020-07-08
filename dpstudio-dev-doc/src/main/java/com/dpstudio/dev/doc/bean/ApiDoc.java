package com.dpstudio.dev.doc.bean;

import java.util.List;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 接口信息
 */
public class ApiDoc {

    /**
     * 所有API模块
     */
    private List<ApiModule> apiModules;

    public ApiDoc(List<ApiModule> apiModules) {
        this.apiModules = apiModules;
    }

    public List<ApiModule> getApiModules() {
        return apiModules;
    }

    public void setApiModules(List<ApiModule> apiModules) {
        this.apiModules = apiModules;
    }

}
