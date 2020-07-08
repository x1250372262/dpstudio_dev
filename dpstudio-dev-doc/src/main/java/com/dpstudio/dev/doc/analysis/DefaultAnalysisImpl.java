package com.dpstudio.dev.doc.analysis;

import com.alibaba.fastjson.JSON;
import com.dpstudio.dev.doc.bean.*;
import com.dpstudio.dev.doc.tag.*;
import com.dpstudio.dev.doc.utils.TagUtils;
import com.dpstudio.dev.utils.BeanUtils;
import net.ymate.platform.commons.json.IJsonArrayWrapper;
import net.ymate.platform.commons.json.IJsonObjectWrapper;
import net.ymate.platform.commons.json.JsonWrapper;
import net.ymate.platform.commons.lang.BlurObject;
import net.ymate.platform.commons.util.ClassUtils;
import net.ymate.platform.commons.util.UUIDUtils;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.base.Type;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 徐建鹏
 * @Date 2018/08/09.
 * @Time: 14:00.
 * @Description: 基于ymp框架, 默认数据解析
 */
public class DefaultAnalysisImpl implements IDocAnalysis {

    @Override
    public List<ApiModule> extend(List<ApiModule> apiModules) {
        apiModules.forEach(apiModule -> {
            apiModule.getApiActions().forEach(apiAction -> {
                apiAction.setId(UUIDUtils.UUID());
                apiAction.setTitle(this.getTitile(apiAction));
                apiAction.setRespbody(this.getRespbody(apiAction));
                apiAction.setParams(this.getParams(apiAction));
                apiAction.setRespParam(this.getResp(apiAction));
                apiAction.setReturnObj(this.getRespObjObjs(apiAction));
                apiAction.setReturnDesc(this.getReturnDesc(apiAction));
                apiAction.setParamObjs(this.getParamObjs(apiAction));
                buildApiAction(apiModule, apiAction);
            });
        });

        return apiModules;
    }

    @Override
    public boolean support(Class<?> clazz) {
        return Objects.nonNull(ClassUtils.getAnnotation(clazz, Controller.class));
    }

    /**
     * 获取@title上的信息
     */
    protected String getTitile(ApiAction apiAction) {
        AbstractDocTag<?> titleTag = TagUtils.findTag(apiAction.getDocTags(), "@title");
        if (titleTag != null) {
            return BlurObject.bind(titleTag.getValues()).toStringValue();
        } else {
            return apiAction.getComment();
        }
    }

    /**
     * 获取@respbody上的信息
     */
    protected String getRespbody(ApiAction apiAction) {
        AbstractDocTag<?> respbodyTag = TagUtils.findTag(apiAction.getDocTags(), "@respbody");
        if (respbodyTag != null) {
            //有@respbody注解 直接返回 否则自动生成
            return BlurObject.bind(respbodyTag.getValues()).toStringValue();
        }
        //没有写@respbody 自动生成
        List<RespInfo> respList = getResp(apiAction);
        List<ObjectInfo> respObjList = getRespObjObjs(apiAction);
        if (respList.isEmpty() && respObjList.isEmpty()) {
            return null;
        }
        IJsonObjectWrapper jsonObject = JsonWrapper.createJsonObject(true);
        for (RespInfo respInfo : respList) {
            fillData(jsonObject, respInfo.getParamType(), respInfo.getDemoValue(), respInfo.getDataKey());
        }
        for (ObjectInfo objectInfo : respObjList) {
            if (StringUtils.isBlank(objectInfo.getDataKey())) {
                continue;
            }
            if ("object".equals(objectInfo.getDataType())) {
                IJsonObjectWrapper object = createObject(objectInfo.getFieldInfos());
                jsonObject.put(objectInfo.getDataKey(), object);
            } else if ("array".equals(objectInfo.getDataType())) {
                IJsonArrayWrapper array = createArray(objectInfo.getFieldInfos());
                jsonObject.put(objectInfo.getDataKey(), array);
            }
        }
        return jsonObject.toString(true,true);
    }

    /**
     * 填充随机数据
     *
     * @param jsonObject
     * @param type
     * @param demoValue
     * @param dataKey
     */
    private void fillData(IJsonObjectWrapper jsonObject, String type, String demoValue, String dataKey) {
        if (Objects.equals(int.class.getName(), type) || Objects.equals(Integer.class.getSimpleName(), type)) {
            if (StringUtils.isNotBlank(demoValue)) {
                jsonObject.put(dataKey, BlurObject.bind(demoValue).toIntValue());
            } else {
                jsonObject.put(dataKey, (int) ((Math.random() * 9 + 1) * 100000));
            }
        } else if (Objects.equals(long.class.getName(), type) || Objects.equals(Long.class.getSimpleName(), type)) {
            if (StringUtils.isNotBlank(demoValue)) {
                jsonObject.put(dataKey, BlurObject.bind(demoValue).toLongValue());
            } else {
                jsonObject.put(dataKey, (long) ((Math.random() * 9 + 1) * 100000));
            }
        } else if (Objects.equals(boolean.class.getName(), type) || Objects.equals(Boolean.class.getSimpleName(), type)) {
            if (StringUtils.isNotBlank(demoValue)) {
                jsonObject.put(dataKey, BlurObject.bind(demoValue).toIntValue());
            } else {
                jsonObject.put(dataKey, false);
            }
        } else if (Objects.equals(double.class.getName(), type) || Objects.equals(Double.class.getSimpleName(), type)) {
            if (StringUtils.isNotBlank(demoValue)) {
                jsonObject.put(dataKey, BlurObject.bind(demoValue).toDoubleValue());
            } else {
                jsonObject.put(dataKey, (Math.random() * 9 + 1) * 100000);
            }
        } else if (Objects.equals(float.class.getName(), type) || Objects.equals(Float.class.getSimpleName(), type)) {
            if (StringUtils.isNotBlank(demoValue)) {
                jsonObject.put(dataKey, BlurObject.bind(demoValue).toFloatValue());
            } else {
                jsonObject.put(dataKey, (float) ((Math.random() * 9 + 1) * 100000));
            }
        } else {
            jsonObject.put(dataKey, StringUtils.defaultIfBlank(demoValue, RandomStringUtils.randomAlphanumeric(10)));
        }
    }

    private IJsonObjectWrapper createObject(List<FieldInfo> fieldInfos) {
        IJsonObjectWrapper jsonObject = JsonWrapper.createJsonObject(true);
        for (FieldInfo fieldInfo : fieldInfos) {
            fillData(jsonObject, fieldInfo.getSimpleTypeName(), fieldInfo.getDemoValue(), fieldInfo.getName());
        }
        return jsonObject;
    }

    private IJsonArrayWrapper createArray(List<FieldInfo> fieldInfos) {
        IJsonArrayWrapper jsonArray = JsonWrapper.createJsonArray();
        for (int i = 0; i < 2; i++) {
            IJsonObjectWrapper jsonObject = JsonWrapper.createJsonObject(true);
            for (FieldInfo fieldInfo : fieldInfos) {
                fillData(jsonObject, fieldInfo.getSimpleTypeName(), fieldInfo.getDemoValue(), fieldInfo.getName());
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    /**
     * 获取@param注释上的信息
     */
    protected List<ParamInfo> getParams(ApiAction apiAction) {
        List<AbstractDocTag<?>> tags = TagUtils.findTags(apiAction.getDocTags(), "@param");
        List<ParamInfo> paramInfos = new ArrayList<>(tags.size());
        for (AbstractDocTag<?> tag : tags) {
            ParamTag paramTag = (ParamTag) tag;
            ParamInfo paramInfo = BeanUtils.copy(paramTag, ParamInfo::new);
            paramInfos.add(paramInfo);
        }
        return paramInfos;
    }

    /**
     * 获取@resp注释上的信息
     */
    protected List<RespInfo> getResp(ApiAction apiAction) {
        List<AbstractDocTag<?>> tags = TagUtils.findTags(apiAction.getDocTags(), "@resp");
        List<RespInfo> list = new ArrayList<>(tags.size());
        for (AbstractDocTag<?> tag : tags) {
            RespTag respTag = (RespTag) tag;
            RespInfo respInfo = BeanUtils.copy(respTag, RespInfo::new);
            respInfo.setDataKey(respTag.getParamName());
            respInfo.setDataType(respTag.getParamType());
            list.add(respInfo);
        }
        return list;
    }

    /**
     * 获取@return注释上的描述语
     */
    protected String getReturnDesc(ApiAction apiAction) {
        AbstractDocTag<?> tag = TagUtils.findTag(apiAction.getDocTags(), "@return");
        return tag != null ? tag.getValues().toString() : null;
    }


    /**
     * 获取@paramObj注解上的对象
     */
    private List<ObjectInfo> getParamObjs(ApiAction apiAction) {
        List<AbstractDocTag<?>> tags = TagUtils.findTags(apiAction.getDocTags(), "@paramObj");
        List<ObjectInfo> paramObjs = new ArrayList<>(tags.size());
        for (AbstractDocTag<?> tag : tags) {
            ParamObjTag paramObjTag = (ParamObjTag) tag;
            paramObjs.add(paramObjTag.getValues());
        }
        return paramObjs;
    }

    /**
     * 获取@respObj注解上的对象
     */
    private List<ObjectInfo> getRespObjObjs(ApiAction apiAction) {
        List<AbstractDocTag<?>> tags = TagUtils.findTags(apiAction.getDocTags(), "@respObj");
        List<ObjectInfo> objectInfos = new ArrayList<>(tags.size());
        for (AbstractDocTag<?> tag : tags) {
            RespObjTag paramObjTag = (RespObjTag) tag;
            objectInfos.add(paramObjTag.getValues());
        }
        return objectInfos;
    }


    /**
     * 构建基于ymp的接口
     *
     * @param apiAction 请求的Action信息
     * @return 封装后的机遇SpringWeb的Action信息
     */
    private void buildApiAction(ApiModule apiModule, ApiAction apiAction) {
        setUriAndMethods(apiModule, apiAction);
    }

    /**
     * 设置请求地址和请求方法
     */
    private void setUriAndMethods(ApiModule apiModule, ApiAction apiAction) {
        Package pack = apiModule.getType().getPackage();
        String packPath = "";
        if(pack != null){
            RequestMapping packMapping = pack.getAnnotation(RequestMapping.class);
            packPath = null;
            if (packMapping != null) {
                packPath = packMapping.value();
            }
        }
        RequestMapping classRequestMapping = apiModule.getType().getAnnotation(RequestMapping.class);
        String parentPath = null;
        if (classRequestMapping != null) {
            parentPath = classRequestMapping.value();
        }
        RequestMapping methodRequestMapping = apiAction.getMethod().getAnnotation(RequestMapping.class);
        if (methodRequestMapping != null) {
            apiAction.setUri(this.getUri(packPath,parentPath, methodRequestMapping.value()));
            apiAction.setMethods(this.getMethods(methodRequestMapping.method()));
        }
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
}
