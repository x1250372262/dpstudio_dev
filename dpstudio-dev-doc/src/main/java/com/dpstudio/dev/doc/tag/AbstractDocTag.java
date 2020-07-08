package com.dpstudio.dev.doc.tag;

/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 注释标签
 */
public abstract class AbstractDocTag<T> {

    /**
     * 标签名称
     */
    private String tagName;

    public AbstractDocTag(String tagName) {
        this.tagName = tagName;
    }

    public AbstractDocTag() {

    }

    /**
     * 获取注解信息
     *
     * @return 获取注解信息
     */
    public abstract T getValues();

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
