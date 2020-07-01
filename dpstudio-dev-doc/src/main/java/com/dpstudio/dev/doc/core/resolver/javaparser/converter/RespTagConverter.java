package com.dpstudio.dev.doc.core.resolver.javaparser.converter;


import com.dpstudio.dev.doc.core.tag.DocTag;
import com.dpstudio.dev.doc.core.tag.ParamTagImpl;
import com.dpstudio.dev.doc.core.tag.RespTagImpl;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: @resp的转换器
 */
public class RespTagConverter extends ParamTagConverter {

    @Override
    public DocTag converter(String comment) {
        ParamTagImpl paramTag = (ParamTagImpl) super.converter(comment);
        RespTagImpl respTag = new RespTagImpl(paramTag.getTagName(), paramTag.getParamName(), paramTag.getParamDesc(),
                paramTag.getParamType(), paramTag.isRequire());
        respTag.setDemoValue(paramTag.getDemoValue());
        return respTag;
    }
}
