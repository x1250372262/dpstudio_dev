package com.dpstudio.dev.doc.core.tag;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 简单文本型注释标签实现
 */
public class DocTagImpl extends DocTag<String> {

    private String value;

    public DocTagImpl(String tagName, String value) {
        super(tagName);
        this.value = value;
    }

    @Override
    public String getValues() {
        return value;
    }
}
