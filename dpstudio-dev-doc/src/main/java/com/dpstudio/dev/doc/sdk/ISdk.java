package com.dpstudio.dev.doc.sdk;

import com.dpstudio.dev.doc.IDocConfig;
import com.dpstudio.dev.doc.annotation.Doc;
import com.dpstudio.dev.doc.bean.ApiResult;
import com.dpstudio.dev.doc.bean.SdkInfo;
import com.dpstudio.dev.doc.exception.DocException;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xujianpeng.
 * @Date: 2020/7/9.
 * @Time: 下午3:40.
 * @Description:
 */
public interface ISdk {

    /**
     * 生成sdk
     *
     * @param apiResult
     * @return
     */
    File create(ApiResult apiResult) throws Exception;

    /**
     * 准备数据
     *
     * @param apiResult
     * @return
     */
    default List<SdkInfo> readyData(ApiResult apiResult) {
        List<SdkInfo> sdkInfoList = new ArrayList<>();
        apiResult.getApiInfoList().forEach(apiInfo -> {
            SdkInfo sdkInfo = new SdkInfo();
            sdkInfo.setComment(apiInfo.getDocName());
            String sdkName = createSdkName(com.dpstudio.dev.doc.Doc.get().getConfig(),apiInfo.getApiModuleList().get(0).getType().getPackage());
            sdkInfo.setName(sdkName);
            List<SdkInfo.ApiInfo> apiInfos = new ArrayList<>();
            apiInfo.getApiModuleList().forEach(apiModule -> {
                apiModule.getApiActions().forEach(apiAction -> {
                    SdkInfo.ApiInfo api = new SdkInfo.ApiInfo();
                    api.setTitle(apiAction.getTitle());
                    api.setUri(apiAction.getUri());
                    api.setMethodName(apiAction.getName());
                    api.setMethod(apiAction.getMethods().get(0));
                    apiInfos.add(api);
                });
            });
            sdkInfo.setApiInfos(apiInfos);
            sdkInfoList.add(sdkInfo);
        });
        return sdkInfoList;
    }

    default String createSdkName(IDocConfig iDocConfig, Package pack) {
        if (iDocConfig.isSdkEnabled()) {
            Doc doc = pack.getAnnotation(Doc.class);
            if (doc == null || StringUtils.isBlank(doc.sdkName())) {
                throw new DocException("开启sdk情况下，Doc注解，sdkName不能为空", new NullPointerException());
            }
            return doc.sdkName();
        }
        return null;
    }
}
