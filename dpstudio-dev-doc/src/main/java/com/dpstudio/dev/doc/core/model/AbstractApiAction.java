package com.dpstudio.dev.doc.core.model;


import java.util.ArrayList;
import java.util.List;

/**
 * @author 徐建鹏
 * @Date 2018/08/09.
 * @Time: 14:00.
 * @Description:
 */
public class AbstractApiAction extends ApiAction {

    /**
     * 访问的uri地址
     */
    private List<String> uris;

    /**
     * 允许的访问方法:POST,GET,DELETE,PUT等, 如果没有,则无限制
     */
    private List<String> methods;

    /**
     * 入参
     */
    private List<ParamInfo> params = new ArrayList<>(0);

    /**
     * 请求参数对象
     */
    private List<ObjectInfo> paramObjs = new ArrayList<>(0);

    /**
     * 返回对象
     */
    private ObjectInfo returnObj;

    /**
     * 出参
     */
    private List<ParamInfo> respParam = new ArrayList<>(0);

    /**
     * 返回描述
     */
    private String returnDesc;

    /**
     * 返回的数据
     */
    private String respbody;

    /**
     * 是否返回json
     */
    private boolean json;


    public List<String> getUris() {
        return uris;
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public List<ParamInfo> getParams() {
        return params;
    }

    public void setParams(List<ParamInfo> params) {
        this.params = params;
    }

    public List<ObjectInfo> getParamObjs() {
        return paramObjs;
    }

    public void setParamObjs(List<ObjectInfo> paramObjs) {
        this.paramObjs = paramObjs;
    }

    public ObjectInfo getReturnObj() {
        return returnObj;
    }

    public void setReturnObj(ObjectInfo returnObj) {
        this.returnObj = returnObj;
    }

    public List<ParamInfo> getRespParam() {
        return respParam;
    }

    public void setRespParam(List<ParamInfo> respParam) {
        this.respParam = respParam;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    public String getRespbody() {
        return respbody;
    }

    public void setRespbody(String respbody) {
        this.respbody = respbody;
    }

    public boolean isJson() {
        return json;
    }

    public void setJson(boolean json) {
        this.json = json;
    }
}
