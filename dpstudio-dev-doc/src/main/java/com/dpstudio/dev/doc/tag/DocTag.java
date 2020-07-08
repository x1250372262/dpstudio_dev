package com.dpstudio.dev.doc.tag;

/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 简单文本型注释标签实现
 */
public class DocTag extends AbstractDocTag<String> {

    private String value;

    public DocTag(String tagName, String value) {
        super(tagName);
        this.value = value;
    }

    @Override
    public String getValues() {
        return value;
    }
}
