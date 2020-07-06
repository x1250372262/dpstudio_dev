package com.dpstudio.dev.doc.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dpstudio.dev.doc.core.DpstudioDoc;
import com.dpstudio.dev.doc.core.model.ApiResult;
import com.dpstudio.dev.doc.ymp.format.HtmlForamt;
import com.dpstudio.dev.doc.ymp.format.MarkdownFormat;
import net.ymate.framework.webmvc.intercept.AjaxAllowCrossDomainInterceptor;
import net.ymate.platform.core.beans.annotation.Before;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("dpstudio/wd/")
@Before(AjaxAllowCrossDomainInterceptor.class)
public class ApiController {

    private static ApiResult apiResult;
    private static final ApiParams apiParams;

    static {
        apiParams = ApiParams.create();
    }

    /**
     * 跳转到接口文档首页
     */
    @RequestMapping(value = "index", method = Type.HttpMethod.GET)
    public IView index() throws Exception {
        apiResult = new ApiService().createDoc();
        return HtmlView.bind(WebMVC.get(), "/dpstudio/wd/index.html");
    }

    /**
     * 生成文档
     */
    @RequestMapping(value = "create", method = Type.HttpMethod.GET)
    public IView create() throws Exception {
        apiResult = new ApiService().createDoc();
        return View.redirectView("/dpstudio/wd/index");
    }


    /**
     * 获取所有文档api
     *
     * @return 系统所有文档接口的数据(json格式)
     */
    @RequestMapping(value = "apis", method = Type.HttpMethod.GET)
    public IView apis() throws Exception{
        if(apiResult == null){
            apiResult = new ApiService().createDoc();
        }
        Object dataStr = JSON.parse(JSON.toJSONString(apiResult, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.PrettyFormat));
        return WebResult.succeed().data(dataStr).attr("apiHost", apiParams.host()).toJSON();
    }

    /**
     * 重新构建文档
     *
     * @return 文档页面
     */
    @RequestMapping(value = "rebuild", method = Type.HttpMethod.GET)
    public IView rebuild() {
        return View.redirectView("/dpstudio/wd/create");
    }

    /**
     * 下载html离线文档
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "download/page", method = Type.HttpMethod.GET)
    public IView downloadHtml() throws Exception {
        if(apiResult == null){
            apiResult = new ApiService().createDoc();
        }
        File tempFile = new File(RuntimeUtils.replaceEnvVariable("${root}/doc/html"), "api.html");
        FileOutputStream out = new FileOutputStream(tempFile);
        HtmlForamt htmlForamt = new HtmlForamt();
        if (apiResult.getApiModuleList() != null && out != null && htmlForamt != null) {
            String s = htmlForamt.format(apiResult);
            try {
                IOUtils.write(s, out, DpstudioDoc.CHARSET);
            } catch (IOException e) {
                Logs.get().getLogger().error("接口文档写入文件失败", e);
            } finally {
                IOUtils.closeQuietly(out);
            }
        }
        tempFile = new File(RuntimeUtils.replaceEnvVariable("${root}/doc/html"), "api.html");
        return View.binaryView(tempFile).useAttachment(apiParams.title() + "_" + apiParams.version() + "_" + DateTimeUtils.formatTime(System.currentTimeMillis(), "_yyyyMMdd_HHmm_ss") + ".html");
    }

    /**
     * 下载markdown离线文档
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "download/mark", method = Type.HttpMethod.GET)
    public IView downloadMark() throws Exception {
        if(apiResult == null){
            apiResult = new ApiService().createDoc();
        }
        File tempFile = new File(RuntimeUtils.replaceEnvVariable("${root}/doc/md"), "api.md");
        FileOutputStream out = new FileOutputStream(tempFile);
        MarkdownFormat markdownFormat = new MarkdownFormat();
        if (apiResult.getApiModuleList() != null && out != null && markdownFormat != null) {
            String s = markdownFormat.format(apiResult);
            try {
                IOUtils.write(s, out, DpstudioDoc.CHARSET);
            } catch (IOException e) {
                Logs.get().getLogger().error("接口文档写入文件失败", e);
            } finally {
                IOUtils.closeQuietly(out);
            }
        }
        tempFile = new File(RuntimeUtils.replaceEnvVariable("${root}//doc/md"), "api.md");
        return View.binaryView(tempFile).useAttachment(apiParams.title() + "_" + apiParams.version() + "_" + DateTimeUtils.formatTime(System.currentTimeMillis(), "_yyyyMMdd_HHmm_ss") + ".md");
    }


}
