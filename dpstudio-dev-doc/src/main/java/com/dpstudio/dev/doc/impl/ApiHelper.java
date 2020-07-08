package com.dpstudio.dev.doc.impl;

import com.dpstudio.dev.doc.IDocConfig;
import com.dpstudio.dev.doc.analysis.DefaultAnalysisImpl;
import com.dpstudio.dev.doc.analysis.IDocAnalysis;
import com.dpstudio.dev.doc.annotation.Doc;
import com.dpstudio.dev.doc.bean.ApiDoc;
import com.dpstudio.dev.doc.bean.ApiInfo;
import com.dpstudio.dev.doc.bean.ApiModule;
import com.dpstudio.dev.doc.bean.ApiResult;
import com.dpstudio.dev.doc.converter.TagConverterHelper;
import com.dpstudio.dev.utils.ListUtils;
import com.dpstudio.dev.utils.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/2/19.
 * @Time: 3:19 下午.
 * @Description:
 */
public class ApiHelper {

    private static final ApiHelper API_HELPER = new ApiHelper();

    public static ApiHelper me() {
        return API_HELPER;
    }

    private static final ApiResult API_RESULT = new ApiResult();

    public ApiResult apiResult() {
        return API_RESULT;
    }


    public void createDoc(IDocConfig iDocConfig) throws Exception {

        ApiDoc apiDoc = create(iDocConfig);
        List<ApiModule> apiModules = apiDoc.getApiModules();
        Map<String, List<ApiModule>> apiModuleMap = ListUtils.groupBy(apiModules, row -> {
            String packageName = row.getType().getPackage().getName();
            Package p = Package.getPackage(packageName);
            if (p == null) {
                return packageName;
            }
            Doc doc = p.getAnnotation(Doc.class);
            if (doc == null || StringUtils.isBlank(doc.name())) {
                return packageName;
            }
            return doc.name();
        });
        List<ApiInfo> apiInfoList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(apiModuleMap)) {
            for (Map.Entry<String, List<ApiModule>> entry : apiModuleMap.entrySet()) {
                ApiInfo apiInfo = new ApiInfo();
                apiInfo.setDocName(entry.getKey());
                apiInfo.setApiModuleList(entry.getValue());
                apiInfoList.add(apiInfo);
            }

        }
        API_RESULT.setApiInfoList(apiInfoList);
        API_RESULT.attr("title", iDocConfig.title())
                .attr("host", iDocConfig.host())
                .attr("version", iDocConfig.version());
        API_RESULT.setApiModuleList(apiModules);
    }

    private static ApiDoc create(IDocConfig iDocConfig) throws Exception {
        if (!iDocConfig.isEnabled()) {
            throw new Exception("doc模块已经禁用");
        }
        return execute();
    }


    private static ApiDoc execute() throws Exception {
        IDocAnalysis iDocAnalysis = new DefaultAnalysisImpl();
        List<ApiModule> apiModules = TagConverterHelper.execute(SourceFileManager.me().findAllFiles(), iDocAnalysis);
        if (!apiModules.isEmpty()) {
            apiModules = iDocAnalysis.extend(apiModules);
        }
        return new ApiDoc(apiModules);
    }
}
