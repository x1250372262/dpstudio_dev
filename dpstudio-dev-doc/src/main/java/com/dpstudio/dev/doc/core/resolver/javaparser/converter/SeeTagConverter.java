package com.dpstudio.dev.doc.core.resolver.javaparser.converter;

import com.dpstudio.dev.doc.core.model.FieldInfo;
import com.dpstudio.dev.doc.core.model.ObjectInfo;
import com.dpstudio.dev.doc.core.resolver.JavaSourceFileManager;
import com.dpstudio.dev.doc.core.tag.DocTag;
import com.dpstudio.dev.doc.core.tag.SeeTagImpl;
import com.dpstudio.dev.doc.core.utils.CommentUtils;
import com.dpstudio.dev.doc.core.utils.JavaFileUtils;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: @see的转换器
 */
public class SeeTagConverter extends DefaultJavaParserTagConverterImpl {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private JavaParser javaParser = new JavaParser();

    @Override
    public DocTag converter(String comment) {
        DocTag docTag = super.converter(comment);

        String path = JavaSourceFileManager.getInstance().getPath((String) docTag.getValues());
        if (StringUtils.isBlank(path)) {
            return null;
        }

        Class<?> returnClassz;
        CompilationUnit cu;
        try (FileInputStream in = new FileInputStream(path)) {
            Optional<CompilationUnit> optional = javaParser.parse(in).getResult();
            if (!optional.isPresent()) {
                return null;
            }
            cu = optional.get();
            if (cu.getTypes().size() <= 0) {
                return null;
            }
            returnClassz = Class.forName(cu.getPackageDeclaration().get().getNameAsString() + "." + cu.getTypes().get(0).getNameAsString());

        } catch (Exception e) {
            log.warn("读取java原文件失败:{}", path, e.getMessage());
            return null;
        }

        String text = cu.getComment().isPresent() ? CommentUtils.parseCommentText(cu.getComment().get().getContent()) : "";

        Map<String, String> commentMap = JavaFileUtils.analysisFieldComments(returnClassz);
        List<FieldInfo> fields = JavaFileUtils.analysisFields(returnClassz, commentMap);

        ObjectInfo objectInfo = new ObjectInfo();
        objectInfo.setType(returnClassz);
        objectInfo.setFieldInfos(fields);
        objectInfo.setComment(text);
        return new SeeTagImpl(docTag.getTagName(), objectInfo);
    }


}
