package com.dpstudio.dev.doc.web;

import com.dpstudio.dev.annotation.Doc;
import com.dpstudio.dev.doc.core.DpstudioDoc;
import com.dpstudio.dev.doc.core.model.ApiDoc;
import com.dpstudio.dev.doc.core.model.ApiInfo;
import com.dpstudio.dev.doc.core.model.ApiModule;
import com.dpstudio.dev.doc.core.model.ApiResult;
import com.dpstudio.dev.doc.ymp.framework.YmpWebFramework;
import com.dpstudio.dev.utils.ListUtils;
import com.dpstudio.dev.utils.ObjectUtils;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.*;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/2/19.
 * @Time: 3:19 下午.
 * @Description:
 */
public class ApiService {

    private static final ApiParams API_PARAMS;
    private static ApiResult apiResult;

    static {
        API_PARAMS = ApiParams.create();
    }

    public ApiResult createDoc() throws Exception {

        ApiDoc apiDoc = create();
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
        apiResult = new ApiResult(apiInfoList)
                .attr("title", API_PARAMS.title())
                .attr("host", API_PARAMS.host())
                .attr("version", API_PARAMS.version());
        apiResult.setApiModuleList(apiModules);
        return apiResult;
    }

    private ApiDoc create() throws Exception {
        if (!API_PARAMS.isInited()) {
            throw new Exception("项目参数配置失败");
        }
        if (!API_PARAMS.isEnable()) {
            throw new Exception("doc模块已经禁用");
        }

        String sourcePath = API_PARAMS.sourcePath();
        if (StringUtils.isBlank(sourcePath)) {
            throw new NullArgumentException("sourcePath不能为空");
        }
        List<String> paths = Arrays.asList(sourcePath.split(","));
        List<File> srcDirs = new ArrayList<>(paths.size());
        for (String s : paths) {
            File dir = new File(s);
            srcDirs.add(dir);
        }
        DpstudioDoc dpstudioDoc = new DpstudioDoc(srcDirs, new YmpWebFramework());
        ApiDoc apiDoc = dpstudioDoc.resolve(API_PARAMS);
//        HashMap<String, Object> properties = new HashMap<String, Object>();
//        properties.put("version", apiParams.version());
//        properties.put("title", apiParams.title());
//        apiDoc.setProperties(properties);
        return apiDoc;
    }
}
