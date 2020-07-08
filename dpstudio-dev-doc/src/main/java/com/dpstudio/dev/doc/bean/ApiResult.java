package com.dpstudio.dev.doc.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 文档结果
 */
public class ApiResult {

    /**
     * 附带的属性
     */
    private Map<String, Object> properties = new HashMap<>();

    /**
     * 所有Doc模块
     */
    private List<ApiInfo> apiInfoList;

    private List<ApiModule> apiModuleList;

    public ApiResult(List<ApiInfo> apiInfoList) {
        this.apiInfoList = apiInfoList;
    }

    public ApiResult(){

    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public List<ApiInfo> getApiInfoList() {
        return apiInfoList;
    }

    public void setApiInfoList(List<ApiInfo> apiInfoList) {
        this.apiInfoList = apiInfoList;
    }

    public Map<String, Object> attrs() {
        return properties;
    }

    @SuppressWarnings("unchecked")
    public <T> T attr(String attrKey) {
        return (T) this.properties.get(attrKey);
    }

    public ApiResult attr(String attrKey, Object attrValue) {
        this.properties.put(attrKey, attrValue);
        return this;
    }

    public ApiResult attrs(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public List<ApiModule> getApiModuleList() {
        return apiModuleList;
    }

    public void setApiModuleList(List<ApiModule> apiModuleList) {
        this.apiModuleList = apiModuleList;
    }
}
