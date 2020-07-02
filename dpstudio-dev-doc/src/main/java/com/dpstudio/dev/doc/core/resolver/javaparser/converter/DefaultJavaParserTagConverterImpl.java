package com.dpstudio.dev.doc.core.resolver.javaparser.converter;


import com.dpstudio.dev.doc.core.tag.DocTag;
import com.dpstudio.dev.doc.core.tag.DocTagImpl;
import com.dpstudio.dev.doc.core.utils.CommentUtils;

import java.util.Objects;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: JavaParser包的默认注释解析转换器
 */
public class DefaultJavaParserTagConverterImpl implements JavaParserTagConverter<String> {

    @Override
    public DocTag converter(String comment) {
        String tagType = CommentUtils.getTagType(comment);
        String coment = comment.substring(Objects.requireNonNull(tagType).length()).trim();
        return new DocTagImpl(tagType, coment);
    }
}
