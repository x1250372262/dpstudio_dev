package com.dpstudio.dev.doc.impl;

import com.dpstudio.dev.doc.IDocConfig;
import com.dpstudio.dev.doc.analysis.DefaultAnalysisImpl;
import com.dpstudio.dev.doc.annotation.Ignore;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import net.ymate.platform.log.ILog;
import net.ymate.platform.log.Logs;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 类源码路径映射管理器
 */
public class SourceFileManager {

    private static final Map<String, String> CLASS_PATH_MAP = new HashMap<>();

    private static final SourceFileManager SOURCE_FILE_MANAGER = new SourceFileManager();

    public static SourceFileManager me() {
        return SOURCE_FILE_MANAGER;
    }

    private static final List<String> FILE_LIST = new ArrayList<>();

    private static final ILog I_LOG;

    static {
        I_LOG = Logs.get();
    }

    public List<String> findAllFiles() {
        return FILE_LIST;
    }

    /**
     * 注册目录中所有的java文件
     *
     * @return 所有java文件
     */
    public void registerFile(IDocConfig iDocConfig) throws Exception {
        if (!FILE_LIST.isEmpty()) {
            return;
        }
        String sourcePath = iDocConfig.sourcePath();
        if (StringUtils.isBlank(sourcePath)) {
            throw new NullArgumentException("sourcePath不能为空");
        }
        List<String> paths = Arrays.asList(sourcePath.split(","));
        List<File> srcDirs = new ArrayList<>(paths.size());
        for (String s : paths) {
            File dir = new File(s);
            srcDirs.add(dir);
        }

        srcDirs.forEach(srcDir -> {
            if (!srcDir.exists()) {
                I_LOG.getLogger().error("源码路径" + srcDir.getAbsolutePath() + "不存在");
                return;
            }
            if (!srcDir.isDirectory()) {
                I_LOG.getLogger().error("源码路径" + srcDir.getAbsolutePath() + "不是一个目录");
                return;
            }
            register(srcDir, iDocConfig.fileName());
        });

        //注册filemap
        for (String file : FILE_LIST) {
            FileInputStream in = new FileInputStream(file);
            Optional<CompilationUnit> optional = new JavaParser().parse(in).getResult();
            if (!optional.isPresent()) {
                continue;
            }
            CompilationUnit cu = optional.get();
            if (cu.getTypes().isEmpty()) {
                continue;
            }
            TypeDeclaration<?> typeDeclaration = cu.getTypes().get(0);
            final Class<?> moduleType = Class.forName(cu.getPackageDeclaration().get().getNameAsString() + "." + typeDeclaration.getNameAsString());
            this.put(moduleType.getName(), file);
            this.put(moduleType.getSimpleName(), file);
        }
    }

    private void register(File file, String fileName) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            String name = file.getName();
            if (name.lastIndexOf(".java") >= 0) {
                List<String> fileFilters = new ArrayList<>();
                if (StringUtils.isNotBlank(fileName)) {
                    fileFilters.addAll(Arrays.asList(fileName.split(",")));
                }
                boolean flag = fileFilters.stream().anyMatch(fileFilter -> name.lastIndexOf(fileFilter) >= 0);
                if (flag) {
                    FILE_LIST.add(file.getAbsolutePath());
                }
            }
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    register(f, fileName);
                }
            }
        }
    }

    public Map<String, String> getFiles() {
        return CLASS_PATH_MAP;
    }

    /**
     * 将指定类名与对应的类源码文件路径存放进来
     *
     * @param name 类名称
     * @param path 类源码文件路径
     */
    public void put(String name, String path) {
        CLASS_PATH_MAP.put(name, path);
    }

    /**
     * 获取指定类名所对应的类源码文件路径
     *
     * @param name 类名
     * @return 源码文件路径, 如果不存在则返回null
     */
    public String get(String name) {
        return CLASS_PATH_MAP.get(name);
    }
}
