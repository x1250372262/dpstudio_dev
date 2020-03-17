package com.dpstudio.dev.doc.web;

import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/2/19.
 * @Time: 12:02 下午.
 * @Description:
 */
public class ApiParams {

    //模块是否禁用
    private boolean isEnable;
    //文档标题
    private String title;
    //代码源码路径用逗号(,)分割
    private String sourcePath;
    //java文件包含名称 用逗号(,)分割
    private String fileName;
    //文档版本
    private String version;
    //接口路径
    private String host;
    //是否初始化参数
    private boolean inited;

    private void init() {
        this.isEnable = BlurObject.bind(YMP.get().getConfig().getParam("doc.enable", "false")).toBooleanValue();
        this.title = YMP.get().getConfig().getParam("doc.title", "文档");
        this.sourcePath = YMP.get().getConfig().getParam("doc.sourcePath", "");
        this.fileName = YMP.get().getConfig().getParam("doc.fileName", "");
        this.version = YMP.get().getConfig().getParam("doc.version", "1.0.0");
        this.host = YMP.get().getConfig().getParam("doc.host", "");
        this.inited = true;
    }

    private ApiParams() {
        if (!isInited()) {
            init();
        }
    }

    public static ApiParams create() {
        return new ApiParams();
    }

    public boolean isInited() {
        return inited;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public String title() {
        return title;
    }

    public String sourcePath() {
        return sourcePath;
    }

    public String fileName() {
        return fileName;
    }

    public String version() {
        return version;
    }

    public String host() {
        return host;
    }
}
