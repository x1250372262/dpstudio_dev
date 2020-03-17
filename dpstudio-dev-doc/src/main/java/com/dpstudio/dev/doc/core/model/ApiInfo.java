package com.dpstudio.dev.doc.core.model;

import java.util.List;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/2/27.
 * @Time: 1:54 下午.
 * @Description:
 */
public class ApiInfo {

    private String docName;

    private List<ApiModule> apiModuleList;

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public List<ApiModule> getApiModuleList() {
        return apiModuleList;
    }

    public void setApiModuleList(List<ApiModule> apiModuleList) {
        this.apiModuleList = apiModuleList;
    }
}
