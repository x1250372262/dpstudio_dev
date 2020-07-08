package com.dpstudio.dev.doc.tag;


import com.dpstudio.dev.doc.bean.ObjectInfo;

/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description:
 */
public class RespObjTag extends AbstractDocTag<ObjectInfo> {

    private ObjectInfo objectInfo;

    public RespObjTag(String tagName, ObjectInfo objectInfo) {
        super(tagName);
        this.objectInfo = objectInfo;
    }

    @Override
    public ObjectInfo getValues() {
        return objectInfo;
    }
}
