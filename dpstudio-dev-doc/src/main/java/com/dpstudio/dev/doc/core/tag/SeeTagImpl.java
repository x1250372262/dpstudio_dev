package com.dpstudio.dev.doc.core.tag;


import com.dpstudio.dev.doc.core.model.ObjectInfo;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: Ree注释的内容封装
 */
public class SeeTagImpl extends DocTag<ObjectInfo> {

    private final ObjectInfo objectInfo;

    public SeeTagImpl(String tagName, ObjectInfo objectInfo) {
        super(tagName);
        this.objectInfo = objectInfo;
    }

    @Override
    public ObjectInfo getValues() {
        return objectInfo;
    }
}
