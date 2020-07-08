package com.dpstudio.dev.doc.converter;

import com.dpstudio.dev.doc.analysis.IDocAnalysis;
import com.dpstudio.dev.doc.annotation.Ignore;
import com.dpstudio.dev.doc.bean.ApiAction;
import com.dpstudio.dev.doc.bean.ApiModule;
import com.dpstudio.dev.doc.impl.SourceFileManager;
import com.dpstudio.dev.doc.tag.AbstractDocTag;
import com.dpstudio.dev.doc.utils.CommentUtils;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import net.ymate.platform.log.ILog;
import net.ymate.platform.log.Logs;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author: mengxiang.
 * @Date: 2020/7/8.
 * @Time: 上午8:38.
 * @Description: 标签转换帮助类
 */
public class TagConverterHelper {

    private static final JavaParser JAVA_PARSER;
    private static final ILog I_LOG;

    static {
        I_LOG = Logs.get();
        JAVA_PARSER = new JavaParser();
    }

    public static List<ApiModule> execute(List<String> files, IDocAnalysis iDocAnalysis) throws Exception {
        List<ApiModule> apiModules = new LinkedList<>();
        for (String file : files) {
            FileInputStream in = new FileInputStream(file);
            Optional<CompilationUnit> optional = JAVA_PARSER.parse(in).getResult();
            if (!optional.isPresent()) {
                continue;
            }
            CompilationUnit cu = optional.get();
            if (cu.getTypes().isEmpty()) {
                continue;
            }
            TypeDeclaration<?> typeDeclaration = cu.getTypes().get(0);
            final Class<?> moduleType = Class.forName(cu.getPackageDeclaration().get().getNameAsString() + "." + typeDeclaration.getNameAsString());
            Ignore ignoreApi = moduleType.getAnnotation(Ignore.class);
            if (ignoreApi != null) {
                continue;
            }
            if (!iDocAnalysis.support(moduleType)) {
                continue;
            }
            ApiModule apiModule = new ApiModule();
            apiModule.setType(moduleType);
            if (typeDeclaration.getComment().isPresent()) {
                String commentText = CommentUtils.parseCommentText(typeDeclaration.getComment().get().getContent());
                commentText = commentText.split("\n")[0].split("\r")[0];
                apiModule.setComment(commentText);
            }

            new VoidVisitorAdapter<Void>() {
                @Override
                public void visit(MethodDeclaration methodDeclaration, Void arg) {
                    Method method = getMethod(moduleType, methodDeclaration);
                    if (method == null) {
                        I_LOG.getLogger().debug("[" + moduleType.getSimpleName() + "]类找不到{" + methodDeclaration.getNameAsString() + "}方法");
                        return;
                    }

                    Optional<Comment> comment = methodDeclaration.getComment();
                    Ignore ignoreApi = method.getAnnotation(Ignore.class);
                    if (ignoreApi != null || !comment.isPresent()) {
                        return;
                    }

                    List<String> comments = CommentUtils.asCommentList(StringUtils.defaultIfBlank(comment.get().getContent(), ""));
                    List<AbstractDocTag<?>> docTagList = new ArrayList<>(comments.size());
                    comments.forEach(c -> {
                        c = StringUtils.trim(c);
                        String tagType = CommentUtils.getTagType(c);
                        if (StringUtils.isBlank(tagType)) {
                            return;
                        }
                        ITagConverter converter = TagConverterRegister.me().findConverter(tagType);
                        AbstractDocTag<?> abstractDocTag = converter.converter(c);
                        if (abstractDocTag != null) {
                            docTagList.add(abstractDocTag);
                        } else {
                            I_LOG.getLogger().warn("识别不了:{" + c + "}");
                        }
                    });
                    ApiAction apiAction = new ApiAction();
                    apiAction.setComment(CommentUtils.parseCommentText(comment.get().getContent()));
                    apiAction.setName(methodDeclaration.getNameAsString());
                    apiAction.setDocTags(docTagList);
                    apiAction.setMethod(method);
                    apiModule.getApiActions().add(apiAction);

                    super.visit(methodDeclaration, arg);
                }
            }.visit(cu, null);
            apiModules.add(apiModule);
        }
        return apiModules;
    }

    /**
     * 获取指定方法的所有入参类型,便于反射
     *
     * @param declaration
     * @return
     */
    private static Method getMethod(Class<?> type, MethodDeclaration declaration) {
        List<Parameter> parameters = Objects.isNull(declaration.getParameters()) ? Collections.emptyList() : declaration.getParameters();
        Method[] methods = type.getDeclaredMethods();
        for (Method method : methods) {
            if (!Objects.equals(method.getName(), declaration.getNameAsString())) {
                continue;
            }
            if (method.getParameterTypes().length != parameters.size()) {
                continue;
            }
            boolean flag = true;
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> paramClass = parameterTypes[i];
                Type parameterType = parameters.get(i).getType();
                if (parameterType == null) {
                    continue;
                }
                String parameterTypeName = parameterType.toString();
                int index = parameterTypeName.lastIndexOf(".");
                if (index > 0) {
                    parameterTypeName = parameterTypeName.substring(index + 1);
                }
                //泛型
                index = parameterTypeName.indexOf("<");
                if (index > 0) {
                    parameterTypeName = parameterTypeName.substring(0, index);
                }
                if (!Objects.equals(paramClass.getSimpleName(), parameterTypeName)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return method;
            }
        }
        return null;
    }
}
