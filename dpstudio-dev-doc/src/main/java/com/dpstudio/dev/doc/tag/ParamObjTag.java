package com.dpstudio.dev.doc.tag;


import com.dpstudio.dev.doc.bean.ObjectInfo;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description:
 */
public class ParamObjTag extends AbstractDocTag<ObjectInfo> {

    private ObjectInfo objectInfo;

    public ParamObjTag(String tagName, ObjectInfo objectInfo) {
        super(tagName);
        this.objectInfo = objectInfo;
    }

    @Override
    public ObjectInfo getValues() {
        return objectInfo;
    }
}
