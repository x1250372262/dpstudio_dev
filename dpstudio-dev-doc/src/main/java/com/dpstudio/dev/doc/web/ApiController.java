package com.dpstudio.dev.doc.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dpstudio.dev.core.R;
import com.dpstudio.dev.core.V;
import com.dpstudio.dev.doc.Doc;
import com.dpstudio.dev.doc.IDoc;
import com.dpstudio.dev.doc.IDocConfig;
import com.dpstudio.dev.doc.bean.ApiResult;
import com.dpstudio.dev.doc.exception.DocException;
import com.dpstudio.dev.doc.format.Format;
import com.dpstudio.dev.doc.format.HtmlForamt;
import com.dpstudio.dev.doc.format.MarkdownFormat;
import net.ymate.platform.commons.util.DateTimeUtils;
import net.ymate.platform.commons.util.RuntimeUtils;
import net.ymate.platform.core.beans.annotation.Before;
import net.ymate.platform.log.ILog;
import net.ymate.platform.log.Logs;
import net.ymate.platform.webmvc.WebMVC;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.cors.CrossDomainInterceptor;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.HtmlView;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("dpstudio/wd/")
@Before(CrossDomainInterceptor.class)
public class ApiController {

    private static ApiResult apiResult;

    private static final IDoc DOC;
    private static final IDocConfig I_DOC_CONFIG;
    private static final ILog I_LOG;

    static {
        DOC = Doc.get();
        I_DOC_CONFIG = DOC.getConfig();
        I_LOG = Logs.get();
    }

    /**
     * 跳转到接口文档首页
     */
    @RequestMapping(value = "index", method = Type.HttpMethod.GET)
    public IView index() throws Exception {
        apiResult = DOC.getDoc();
        return HtmlView.bind(WebMVC.get(), "/dpstudio/wd/index.html");
    }

    /**
     * 生成文档
     */
    @RequestMapping(value = "create", method = Type.HttpMethod.GET)
    public IView create() throws Exception {
        apiResult = DOC.getDoc();
        return View.redirectView("/dpstudio/wd/index");
    }


    /**
     * 获取所有文档api
     *
     * @return 系统所有文档接口的数据(json格式)
     */
    @RequestMapping(value = "apis", method = Type.HttpMethod.GET)
    public IView apis() throws Exception {
        if (apiResult == null) {
            apiResult = DOC.getDoc();
        }
        Object dataStr = JSON.parse(JSON.toJSONString(apiResult, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.PrettyFormat));
        return V.view(R.ok().attr("data", dataStr).attr("apiHost", I_DOC_CONFIG.host()));

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
        if (apiResult == null) {
            apiResult = DOC.getDoc();
        }
        File tempFileDir = new File(RuntimeUtils.replaceEnvVariable("${root}/doc/html"));
        if(!tempFileDir.exists()){
            tempFileDir.mkdirs();
        }
        File tempFile = new File(tempFileDir, "api.html");
        FileOutputStream out = new FileOutputStream(tempFile);
        Format format = new HtmlForamt();
        if (apiResult.getApiModuleList() != null) {
            String str = format.format(apiResult);
            try {
                IOUtils.write(str, out, IDoc.CHARSET);
            } catch (IOException e) {
                I_LOG.getLogger().error("生成html文档失败", e);
                throw new DocException("生成html文档失败", e);
            } finally {
                out.close();
            }
        }
        tempFile = new File(tempFileDir, "api.html");
        return View.binaryView(tempFile).useAttachment(I_DOC_CONFIG.title() + "_" + I_DOC_CONFIG.version() + "_" + DateTimeUtils.formatTime(System.currentTimeMillis(), "_yyyyMMdd_HHmm_ss") + ".html");
    }

    /**
     * 下载markdown离线文档
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "download/mark", method = Type.HttpMethod.GET)
    public IView downloadMark() throws Exception {
        if (apiResult == null) {
            apiResult = DOC.getDoc();
        }
        File tempFileDir = new File(RuntimeUtils.replaceEnvVariable("${root}/doc/md"));
        if(!tempFileDir.exists()){
            tempFileDir.mkdirs();
        }
        File tempFile = new File(tempFileDir, "api.md");
        FileOutputStream out = new FileOutputStream(tempFile);
        Format format = new MarkdownFormat();
        if (apiResult.getApiModuleList() != null) {
            String str = format.format(apiResult);
            try {
                IOUtils.write(str, out, IDoc.CHARSET);
            } catch (IOException e) {
                I_LOG.getLogger().error("生成markdown文档失败", e);
                throw new DocException("生成markdown文档失败", e);
            } finally {
                out.close();
            }
        }
        tempFile = new File(tempFileDir, "api.md");
        return View.binaryView(tempFile).useAttachment(I_DOC_CONFIG.title() + "_" + I_DOC_CONFIG.version() + "_" + DateTimeUtils.formatTime(System.currentTimeMillis(), "_yyyyMMdd_HHmm_ss") + ".md");
    }


}
