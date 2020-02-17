package com.dpstudio.dev.doc.web;

import com.dpstudio.dev.doc.core.DpstudioDoc;
import com.dpstudio.dev.doc.core.model.ApiDoc;
import com.dpstudio.dev.doc.ymp.format.HtmlForamt;
import com.dpstudio.dev.doc.ymp.format.MarkdownFormat;
import com.dpstudio.dev.doc.ymp.framework.YmpWebFramework;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.DateTimeUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.log.Logs;
import net.ymate.platform.webmvc.WebMVC;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.HtmlView;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("dpstudio/api/v1/")
public class ApiController {

    private static ApiDoc apiDoc;
    private static Boolean isEnable = false;
    private static String title = "文档";
    private static String sourcePath = "";
    private static String baseSourcePath="";
    private static String version = "1.0.0";
    private static String host = "";
    private static String apiHost = "";
    private static Boolean inited = false;

    private Boolean config() {
        isEnable = BlurObject.bind(YMP.get().getConfig().getParam("doc.enable")).toBooleanValue();
        title = YMP.get().getConfig().getParam("doc.title");
        sourcePath = YMP.get().getConfig().getParam("doc.sourcePath");
        baseSourcePath = YMP.get().getConfig().getParam("doc.baseSourcePath");
        version = YMP.get().getConfig().getParam("doc.version");
        host = YMP.get().getConfig().getParam("doc.host");
        apiHost = YMP.get().getConfig().getParam("doc.apiHost");
        inited = true;
        return inited;
    }

    /**
     * 跳转到接口文档首页
     */
    @RequestMapping(value = "index", method = Type.HttpMethod.GET)
    public IView index() throws Exception {
        if (!inited) {
            init();
        }
        return HtmlView.bind(WebMVC.get(), "/dpsapi/index.html");
    }

    /**
     * 生成文档
     */
    @RequestMapping(value = "create", method = Type.HttpMethod.GET)
    public IView create() throws Exception {
        init();
        return View.redirectView("/dps/api/index");
    }

    /**
     * 获取所有文档api
     *
     * @return 系统所有文档接口的数据(json格式)
     */
    @RequestMapping(value = "apis", method = Type.HttpMethod.GET)
    public IView apis() {
        return WebResult.succeed().data(apiDoc).attr("apiHost", apiHost).toJSON();
    }

    /**
     * 重新构建文档
     *
     * @return 文档页面
     */
    @RequestMapping(value = "rebuild", method = Type.HttpMethod.GET)
    public IView rebuild() throws Exception {
        return View.redirectView("/dps/api/create");
    }

    /**
     * 下载html离线文档
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "download/page", method = Type.HttpMethod.GET)
    public IView downloadHtml() throws Exception {
        File tempFile = new File(RuntimeUtils.replaceEnvVariable("${root}/upload_files"), "api.html");
        FileOutputStream out = new FileOutputStream(tempFile);
        HtmlForamt htmlForamt = new HtmlForamt();
        if (apiDoc.getApiModules() != null && out != null && htmlForamt != null) {
            String s = htmlForamt.format(apiDoc);
            try {
                IOUtils.write(s, out, DpstudioDoc.CHARSET);
            } catch (IOException e) {
                Logs.get().getLogger().error("接口文档写入文件失败", e);
            } finally {
                IOUtils.closeQuietly(out);
            }
        }
        tempFile = new File(RuntimeUtils.replaceEnvVariable("${root}/upload_files"), "api.html");
        return View.binaryView(tempFile).useAttachment(title + "_" + version + "_" + DateTimeUtils.formatTime(System.currentTimeMillis(), "_yyyyMMdd_HHmm_ss") + ".html");
    }

    /**
     * 下载markdown离线文档
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "download/mark", method = Type.HttpMethod.GET)
    public IView downloadMark() throws Exception {
        File tempFile = new File(RuntimeUtils.replaceEnvVariable("${root}/upload_files"), "api.md");
        FileOutputStream out = new FileOutputStream(tempFile);
        MarkdownFormat markdownFormat = new MarkdownFormat();
        if (apiDoc.getApiModules() != null && out != null && markdownFormat != null) {
            String s = markdownFormat.format(apiDoc);
            try {
                IOUtils.write(s, out, DpstudioDoc.CHARSET);
            } catch (IOException e) {
                Logs.get().getLogger().error("接口文档写入文件失败", e);
            } finally {
                IOUtils.closeQuietly(out);
            }
        }
        tempFile = new File(RuntimeUtils.replaceEnvVariable("${root}/upload_files"), "api.md");
        return View.binaryView(tempFile).useAttachment(title + "_" + version + "_" + DateTimeUtils.formatTime(System.currentTimeMillis(), "_yyyyMMdd_HHmm_ss") + ".md");
    }


    public void init() throws Exception {
        if (!config()) {
            throw new Exception("初始化项目失败");
        }
        if(!isEnable){
            throw new Exception("doc模块已经禁用");
        }

        if (StringUtils.isBlank(sourcePath)) {
            throw new NullPointerException("sourcePath不能为空");
        }
        List<String> paths = Arrays.asList(sourcePath.split(","));
        List<File> srcDirs = new ArrayList<>(paths.size());
        try {
            for (String s : paths) {
                if(StringUtils.isNotBlank(baseSourcePath)){
                    s = baseSourcePath.concat(s);
                }
                File dir = new File(s);
                srcDirs.add(dir);
            }
        } catch (Exception e) {
            Logs.get().getLogger().error("获取源码目录路径错误", e);
            return;
        }
        try {
            DpstudioDoc dpstudioDoc = new DpstudioDoc(srcDirs, new YmpWebFramework());
            apiDoc = dpstudioDoc.resolve();
            HashMap<String, Object> properties = new HashMap<String, Object>();
            properties.put("version", version);
            properties.put("title", title);
            apiDoc.setProperties(properties);
            Logs.get().getLogger().info("开始生成文档");
        } catch (Exception e) {
            Logs.get().getLogger().error("生成文档错误", e);
        }
    }
}
