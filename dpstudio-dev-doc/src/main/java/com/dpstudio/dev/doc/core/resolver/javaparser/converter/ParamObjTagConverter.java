package com.dpstudio.dev.doc.core.resolver.javaparser.converter;


import com.dpstudio.dev.doc.core.tag.DocTag;
import com.dpstudio.dev.doc.core.tag.ParamObjTagImpl;
import com.dpstudio.dev.doc.core.tag.SeeTagImpl;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description:
 */

public class ParamObjTagConverter extends SeeTagConverter {

    @Override
    public DocTag converter(String comment) {
        SeeTagImpl seeTag = (SeeTagImpl) super.converter(comment);
        if (seeTag != null) {
            return new ParamObjTagImpl(seeTag.getTagName(), seeTag.getValues());
        }
        return null;
    }
}
