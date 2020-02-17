package com.dpstudio.dev.doc.core.model;

import com.dpstudio.dev.doc.core.tag.DocTag;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 接口信息, 一个接口类里面会有多个接口, 每个接口都抽象成ApiAction
 */
public class ApiAction {

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
    private List<DocTag> docTags;

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

    public List<DocTag> getDocTags() {
        return docTags;
    }

    public void setDocTags(List<DocTag> docTags) {
        this.docTags = docTags;
    }
}
