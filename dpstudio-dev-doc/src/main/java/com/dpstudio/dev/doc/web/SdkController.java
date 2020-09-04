package com.dpstudio.dev.doc.web;

import com.dpstudio.dev.doc.Doc;
import com.dpstudio.dev.doc.IDoc;
import com.dpstudio.dev.doc.IDocConfig;
import com.dpstudio.dev.doc.bean.ApiResult;
import com.dpstudio.dev.doc.exception.DocException;
import com.dpstudio.dev.doc.sdk.SdkFactory;
import net.ymate.platform.core.beans.annotation.Before;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.cors.CrossDomainInterceptor;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;

import java.io.File;

/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 文档控制器
 */
@Controller
@RequestMapping("dpstudio/wd/sdk")
@Before(CrossDomainInterceptor.class)
public class SdkController {

    private static ApiResult apiResult;

    private static final IDoc DOC;
    private static final IDocConfig I_DOC_CONFIG;

    static {
        DOC = Doc.get();
        I_DOC_CONFIG = DOC.getConfig();
    }

    /**
     * 下载sdk
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "download", method = Type.HttpMethod.GET)
    public IView download(@RequestParam(defaultValue = "all") String type) throws Exception {
        if (!I_DOC_CONFIG.isSdkEnabled()) {
            return View.textView("sdk功能未开启");
        }
        if (apiResult == null) {
            apiResult = DOC.getDoc();
        }
        File file = SdkFactory.get(SdkFactory.SDK_TYPE.valueTo(type)).create(apiResult);
        if (file == null) {
            throw new DocException("文件生成失败", new NullPointerException());
        }
        return View.binaryView(file).useAttachment("sdk.js");
    }


}
