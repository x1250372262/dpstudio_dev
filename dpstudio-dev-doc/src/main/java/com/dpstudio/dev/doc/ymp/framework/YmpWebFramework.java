package com.dpstudio.dev.doc.ymp.framework;

import com.dpstudio.dev.doc.core.format.AbstractWebFramework;
import com.dpstudio.dev.doc.core.model.AbstractApiAction;
import com.dpstudio.dev.doc.core.model.ApiAction;
import com.dpstudio.dev.doc.core.model.ApiModule;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.base.Type;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 徐建鹏
 * @Date 2018/08/09.
 * @Time: 14:00.
 * @Description: 基于ymp框架, 扩展api数据
 */
public class YmpWebFramework extends AbstractWebFramework {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean support(Class<?> classz) {
        if (classz.getAnnotation(Controller.class) != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<ApiModule> extend(List<ApiModule> apiModules) {
        apiModules = super.extend(apiModules);
        List<ApiModule> newApiModules = new ArrayList<>();

        for (ApiModule apiModule : apiModules) {

            ApiModule newApiModule = new ApiModule();
            newApiModule.setComment(apiModule.getComment());
            newApiModule.setType(apiModule.getType());
            boolean isjson = this.isJson(apiModule.getType());

            for (ApiAction apiAction : apiModule.getApiActions()) {
                AbstractApiAction saa = this.buildYmpApiAction(newApiModule, apiAction, isjson);
                if (saa != null) {
                    newApiModule.getApiActions().add(saa);
                }
            }

            newApiModules.add(newApiModule);
        }
        return newApiModules;
    }

    /**
     * 构建基于ymp的接口
     *
     * @param apiAction 请求的Action信息
     * @param isjson    是否json
     * @return 封装后的机遇SpringWeb的Action信息
     */
    private AbstractApiAction buildYmpApiAction(ApiModule apiModule, ApiAction apiAction, boolean isjson) {

        AbstractApiAction saa = new AbstractApiAction();
        try {
            ClassUtils.wrapper(apiAction).duplicate(saa);
        } catch (Exception e) {
            logger.error("copy ApiAction to HttpApiAction properties error", e);
            return null;
        }
        saa.setJson(true);
        boolean isMappingMethod = this.setUrisAndMethods(apiModule, apiAction, saa);
        if (!isMappingMethod) {
            return null;
        }
        return saa;
    }

    /**
     * 设置请求地址和请求方法
     */
    private boolean setUrisAndMethods(ApiModule apiModule, ApiAction apiAction, AbstractApiAction saa) {
        Package pack = apiModule.getType().getPackage();
        String packPath = "";
        if(pack != null){
            RequestMapping packMapping = pack.getAnnotation(RequestMapping.class);
            packPath = null;
            if (packMapping != null) {
                packPath = packMapping.value();
            }
        }
        RequestMapping classRequestMappingAnno = apiModule.getType().getAnnotation(RequestMapping.class);
        String parentPath = null;
        if (classRequestMappingAnno != null) {
            parentPath = classRequestMappingAnno.value();
        }
        RequestMapping methodRequestMappingAnno = apiAction.getMethod().getAnnotation(RequestMapping.class);
        if (methodRequestMappingAnno != null) {
            saa.setUri(this.getUri(packPath,parentPath, methodRequestMappingAnno.value()));
            saa.setMethods(this.getMethods(methodRequestMappingAnno.method()));
            return true;
        }
        return false;
    }

    /**
     * 获取接口的uri
     *
     * @return
     */
    protected String getUri(String packPath,String parentPath, String values) {
        String uri;
        if(StringUtils.isNotBlank(packPath)){
            if(packPath.startsWith("/")){
                packPath = packPath.substring(1);
            }
            if(packPath.endsWith("/")){
                packPath = packPath.substring(0,packPath.length()-1);
            }
            parentPath = packPath.concat("/").concat(parentPath);
        }
        if (parentPath.endsWith("/") && values.startsWith("/")) {
            uri = parentPath.substring(0, parentPath.length() - 1) + values;
        } else if (parentPath.length() > 0 && !parentPath.endsWith("/") && !values.startsWith("/")) {
            uri = parentPath + '/' + values;
        } else {
            uri = parentPath + values;
        }
        return uri;
    }

    /**
     * 获取接口上允许的访问方式
     */
    protected List<String> getMethods(Type.HttpMethod... methods) {
        List<String> methodStrs = new ArrayList<>();
        for (Type.HttpMethod requestMethod : methods) {
            methodStrs.add(requestMethod.name());
        }
        return methodStrs;
    }

    /**
     * 判断整个类里的所有接口是否都返回json
     */
    protected boolean isJson(Class<?> classz) {
        return true;
    }
}
