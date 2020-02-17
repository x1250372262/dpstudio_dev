package com.dpstudio.dev.doc.core;

import com.dpstudio.dev.doc.core.format.Format;
import com.dpstudio.dev.doc.core.framework.Framework;
import com.dpstudio.dev.doc.core.model.ApiDoc;
import com.dpstudio.dev.doc.core.model.ApiModule;
import com.dpstudio.dev.doc.core.resolver.DocTagResolver;
import com.dpstudio.dev.doc.core.resolver.JavaSourceFileManager;
import com.dpstudio.dev.doc.core.resolver.javaparser.JavaParserDocTagResolver;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description:
 */
public class DpstudioDoc {

    public static final String CHARSET = "utf-8";

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 源码路径
     */
    private List<File> srcDirs;

    /**
     * api框架类型
     */
    private Framework framework;

    /**
     * 默认的java注释解析器实现
     * <p>
     * 备注:基于sun doc的解析方式已经废弃,若需要请参考v1.0之前的版本
     *
     * @see org.treeleafj.xdoc.resolver.javaparser.JavaParserDocTagResolver
     */
    private DocTagResolver docTagResolver = new JavaParserDocTagResolver();

    /**
     * 构建Doc对象
     *
     * @param srcDir 源码目录路径
     */
    public DpstudioDoc(File srcDir, Framework framework) {
        this(Arrays.asList(srcDir), framework);
    }


    /**
     * 构建Doc对象
     *
     * @param srcDirs 源码目录路径,支持多个
     */
    public DpstudioDoc(List<File> srcDirs, Framework framework) {
        this.srcDirs = srcDirs;
        this.framework = framework;
    }

    /**
     * 解析源码并返回对应的接口数据
     *
     * @return API接口数据
     */
    public ApiDoc resolve() {
        List<String> files = new ArrayList<>();
        for (File dir : this.srcDirs) {

            if (!dir.exists()) {
                logger.error("源码路径[{}]不存在", dir.getAbsolutePath());
                continue;
            }

            if (!dir.isDirectory()) {
                logger.error("源码路径[{}]不是一个目录", dir.getAbsolutePath());
                continue;
            }

            logger.info("开始解析源码路径:{}", dir.getAbsolutePath());
            files.addAll(JavaSourceFileManager.getInstance().getAllJavaFiles(dir));
        }

        List<ApiModule> apiModules = this.docTagResolver.resolve(files, framework);

        if (framework != null && apiModules != null) {
            apiModules = framework.extend(apiModules);
        }
        return new ApiDoc(apiModules);
    }

    /**
     * 构建接口文档
     *
     * @param out    输出位置
     * @param format 文档格式
     */
    public void build(OutputStream out, Format format) {
        this.build(out, format, null);
    }

    /**
     * 构建接口文档
     *
     * @param out        输出位置
     * @param format     文档格式
     * @param properties 文档属性
     */
    public void build(OutputStream out, Format format, Map<String, Object> properties) {
        ApiDoc apiDoc = this.resolve();
        if (properties != null) {
            apiDoc.getProperties().putAll(properties);
        }

        if (apiDoc.getApiModules() != null && out != null && format != null) {
            String s = format.format(apiDoc);
            try {
                IOUtils.write(s, out, CHARSET);
            } catch (IOException e) {
                logger.error("接口文档写入文件失败", e);
            } finally {
                IOUtils.closeQuietly(out);
            }
        }
    }


    public void setFramework(Framework framework) {
        this.framework = framework;
    }

    public void setDocTagResolver(DocTagResolver docTagResolver) {
        this.docTagResolver = docTagResolver;
    }
}
