package com.dpstudio.dev.doc.core.utils;

import com.dpstudio.dev.doc.core.model.FieldInfo;
import com.dpstudio.dev.doc.core.resolver.JavaSourceFileManager;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.util.*;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: Java文件工具
 */
public class JavaFileUtils {

    private static Logger logger = LoggerFactory.getLogger(JavaFileUtils.class);

    private static JavaParser javaParser = new JavaParser();

    public static Map<String, String> analysisFieldComments(Class<?> classz) {

        final Map<String, String> commentMap = new HashMap(10);

        List<Class> classes = new LinkedList<>();

        Class nowClass = classz;

        //获取所有的属性注释(包括父类的)
        while (true) {
            classes.add(0, nowClass);
            if (Object.class.equals(nowClass) || Object.class.equals(nowClass.getSuperclass())) {
                break;
            }
            nowClass = nowClass.getSuperclass();
        }

        //反方向循环,子类属性注释覆盖父类属性
        for (Class clz : classes) {
            String path = JavaSourceFileManager.getInstance().getPath(clz.getName());
            if (StringUtils.isBlank(path)) {
                continue;
            }
            try (FileInputStream in = new FileInputStream(path)) {
                CompilationUnit cu = javaParser.parse(in).getResult().get();

                new VoidVisitorAdapter<Void>() {
                    @Override
                    public void visit(FieldDeclaration n, Void arg) {
                        String name = n.getVariable(0).getName().asString();

                        String comment = "";
                        if (n.getComment().isPresent()) {
                            comment = n.getComment().get().getContent();
                        }

                        if (name.contains("=")) {
                            name = name.substring(0, name.indexOf("=")).trim();
                        }

                        commentMap.put(name, CommentUtils.parseCommentText(comment));
                    }
                }.visit(cu, null);

            } catch (Exception e) {
                logger.warn("读取java原文件失败:{}", path, e.getMessage(), e);
            }
        }

        return commentMap;
    }

    public static List<FieldInfo> analysisFields(Class classz, Map<String, String> commentMap) {
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(classz);


        List<FieldInfo> fields = new ArrayList<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            //排除掉class属性
            if ("class".equals(propertyDescriptor.getName())) {
                continue;
            }

            FieldInfo field = new FieldInfo();
            field.setType(propertyDescriptor.getPropertyType());
            field.setSimpleTypeName(propertyDescriptor.getPropertyType().getSimpleName());
            field.setName(propertyDescriptor.getName());
            String comment = commentMap.get(propertyDescriptor.getName());
            if (StringUtils.isBlank(comment)) {
                field.setComment("");
                field.setRequire(false);
                fields.add(field);
            } else {
                boolean require = false;
                String demoValue = "";
                if (comment.contains("|")) {
                    String[] commentArr = comment.split("\\|");
                    comment = commentArr[0];
                    if(commentArr.length==2){
                        String requireString = commentArr[1];
                        require = Constant.YES_ZH.equals(requireString) || Constant.YES_EN.equalsIgnoreCase(requireString);
                    }else if(commentArr.length==3){
                        String requireString = commentArr[1];
                        require = Constant.YES_ZH.equals(requireString) || Constant.YES_EN.equalsIgnoreCase(requireString);
                        demoValue = commentArr[2];
                    }
                }

                field.setComment(comment);
                field.setRequire(require);
                field.setDemoValue(demoValue);
                fields.add(field);
            }
        }
        return fields;
    }
}
