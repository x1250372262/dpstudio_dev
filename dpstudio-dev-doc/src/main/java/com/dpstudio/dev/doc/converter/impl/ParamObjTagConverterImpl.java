package com.dpstudio.dev.doc.converter.impl;


import com.dpstudio.dev.doc.converter.ITagConverter;
import com.dpstudio.dev.doc.tag.ObjectTag;
import com.dpstudio.dev.doc.tag.ParamObjTag;
import net.ymate.platform.log.Logs;

/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description:
 */

public class ParamObjTagConverterImpl implements ITagConverter {

    @Override
    public ParamObjTag converter(String comment) {
        ObjectTag objectTag = null;
        try {
            objectTag = (ObjectTag) objectConverter(comment);
        } catch (Exception e) {
            Logs.get().getLogger().error("解析失败:" + e.getMessage());
        }
        if (objectTag != null) {
            return new ParamObjTag(objectTag.getTagName(), objectTag.getValues());
        }
        return null;
    }
}
