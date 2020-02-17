package com.dpstudio.dev.doc.core.resolver.javaparser.converter;


import com.dpstudio.dev.doc.core.tag.DocTag;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 针对JavaParser语法解析包解析出来的文本转换器,负责将文本转转DocTag
 */
public interface JavaParserTagConverter<T extends String> {

    /**
     * 将指定的文本转义为DocTag
     *
     * @param o 文本
     * @return DocTag对象
     */
    DocTag converter(T o);
}
