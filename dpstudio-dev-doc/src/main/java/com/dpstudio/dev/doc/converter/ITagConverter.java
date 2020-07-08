package com.dpstudio.dev.doc.converter;


import com.dpstudio.dev.doc.bean.FieldInfo;
import com.dpstudio.dev.doc.bean.ObjectInfo;
import com.dpstudio.dev.doc.converter.impl.DefaultTagConverterImpl;
import com.dpstudio.dev.doc.impl.SourceFileManager;
import com.dpstudio.dev.doc.tag.AbstractDocTag;
import com.dpstudio.dev.doc.tag.DocTag;
import com.dpstudio.dev.doc.tag.ObjectTag;
import com.dpstudio.dev.doc.utils.CommentUtils;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.Comment;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 转换器接口
 */
public interface ITagConverter {

    JavaParser JAVA_PARSER = new JavaParser();

    /**
     * 把文本转换成标签
     *
     * @param comment 文本
     * @return AbstractDocTag
     */
    AbstractDocTag<?> converter(String comment);


    /**
     * 对象转换
     *
     * @param comment
     * @return
     */
    default AbstractDocTag<?> objectConverter(String comment) throws Exception {
        DocTag docTag = new DefaultTagConverterImpl().converter(comment);
        String values = docTag.getValues();
        String valueStr = docTag.getValues();
        String dataKey = "";
        String dataType = "";
        if (values.contains("|")) {
            String[] valuesArr = values.split("\\|");
            valueStr = valuesArr[0];
            if (valuesArr.length > 2) {
                dataKey = valuesArr[1];
                dataType = valuesArr[2];
            } else if (valuesArr.length > 1) {
                dataKey = valuesArr[1];
            }
        }
        String path = SourceFileManager.me().get(valueStr);
        if (StringUtils.isBlank(path)) {
            return null;
        }

        FileInputStream in = new FileInputStream(path);
        Optional<CompilationUnit> optional = JAVA_PARSER.parse(in).getResult();
        if (!optional.isPresent()) {
            return null;
        }
        CompilationUnit cu = optional.get();
        if (cu.getTypes().size() <= 0) {
            return null;
        }
        Optional<Comment> commentBean = cu.getComment();
        Class<?> returnClazz = Class.forName(cu.getPackageDeclaration().get().getNameAsString() + "." + cu.getTypes().get(0).getNameAsString());

        String text = cu.getComment().isPresent() ? CommentUtils.parseCommentText(commentBean.get().getContent()) : "";

        Map<String, String> commentMap = CommentUtils.fileCommentMap(returnClazz);
        List<FieldInfo> fields = CommentUtils.findFieldInfo(returnClazz, commentMap);

        ObjectInfo objectInfo = new ObjectInfo();
        objectInfo.setType(returnClazz);
        objectInfo.setFieldInfos(fields);
        objectInfo.setComment(text);
        objectInfo.setDataKey(dataKey);
        objectInfo.setDataType(dataType);
        return new ObjectTag(docTag.getTagName(), objectInfo);

    }
}
