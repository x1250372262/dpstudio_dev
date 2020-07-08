package com.dpstudio.dev.doc.converter.impl;


import com.dpstudio.dev.doc.converter.ITagConverter;
import com.dpstudio.dev.doc.tag.ParamTag;
import com.dpstudio.dev.doc.tag.RespTag;
import com.dpstudio.dev.utils.BeanUtils;

/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: @resp的转换器
 */
public class RespTagConverterImpl implements ITagConverter {

    @Override
    public RespTag converter(String comment) {
        ParamTag paramTag = new ParamTagConverterImpl().converter(comment);
        return BeanUtils.copy(paramTag, RespTag::new);
    }
}
