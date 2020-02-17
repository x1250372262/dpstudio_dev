package com.dpstudio.dev.doc.core.tag;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 注释标签
 */
public abstract class DocTag<T> {

    /**
     * 标签名称
     */
    private String tagName;

    public DocTag(String tagName) {
        this.tagName = tagName;
    }

    public DocTag(){

    }
    public abstract T getValues();

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
