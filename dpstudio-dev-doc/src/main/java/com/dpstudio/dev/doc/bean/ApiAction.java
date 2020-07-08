package com.dpstudio.dev.doc.bean;

import com.dpstudio.dev.doc.tag.AbstractDocTag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 接口信息, 一个接口类里面会有多个接口, 每个接口都抽象成ApiAction
 */
public class ApiAction {

    /**
     * id唯一标识
     */
    private String id;

    /**
     * 展示用的标题
     */
    private String title;

    /**
     * 接口方法名称
     */
    private String name;

    /**
     * 接口方法
     */
    @JsonIgnore
    private Method method;

    /**
     * 接口的描述
     */
    private String comment;

    /**
     * 方法上标注的注解
     */
    private List<AbstractDocTag<?>> docTags;

    /**
     * 访问的uri地址
     */
    private String uri;

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
    private List<ObjectInfo> returnObj = new ArrayList<>(0);

    /**
     * 出参
     */
    private List<RespInfo> respParam = new ArrayList<>(0);

    /**
     * 返回描述
     */
    private String returnDesc;

    /**
     * 返回的数据
     */
    private String respbody;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<AbstractDocTag<?>> getDocTags() {
        return docTags;
    }

    public void setDocTags(List<AbstractDocTag<?>> docTags) {
        this.docTags = docTags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public List<ObjectInfo> getReturnObj() {
        return returnObj;
    }

    public void setReturnObj(List<ObjectInfo> returnObj) {
        this.returnObj = returnObj;
    }

    public List<RespInfo> getRespParam() {
        return respParam;
    }

    public void setRespParam(List<RespInfo> respParam) {
        this.respParam = respParam;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = StringUtils.defaultIfBlank(returnDesc,"");
    }

    public String getRespbody() {
        return respbody;
    }

    public void setRespbody(String respbody) {
        this.respbody = StringUtils.defaultIfBlank(respbody,"");
    }

}
