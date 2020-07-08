package com.dpstudio.dev.doc.tag;


import com.dpstudio.dev.doc.bean.ObjectInfo;

/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 对象标签默认实现
 */
public class ObjectTag extends AbstractDocTag<ObjectInfo> {

    private final ObjectInfo objectInfo;

    public ObjectTag(String tagName, ObjectInfo objectInfo) {
        super(tagName);
        this.objectInfo = objectInfo;
    }

    @Override
    public ObjectInfo getValues() {
        return objectInfo;
    }
}
