package com.dpstudio.dev.doc.core.format;

import com.alibaba.fastjson.JSONObject;
import com.dpstudio.dev.doc.core.framework.Framework;
import com.dpstudio.dev.doc.core.model.*;
import com.dpstudio.dev.doc.core.tag.*;
import com.dpstudio.dev.doc.core.utils.TagUtils;
import net.ymate.platform.core.util.ClassUtils;
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
public abstract class AbstractWebFramework implements Framework {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<ApiModule> extend(List<ApiModule> apiModules) {
        for (ApiModule apiModule : apiModules) {
            for (int i = 0; i < apiModule.getApiActions().size(); i++) {
                ApiAction oldApiAction = apiModule.getApiActions().get(i);
                AbstractApiAction ympApiAction = new AbstractApiAction();
                try {
                    ClassUtils.wrapper(oldApiAction).duplicate(ympApiAction);
                } catch (Exception e) {
                    logger.error("copy ApiAction to HttpApiAction properties error", e);
                    return new ArrayList<>(0);
                }

                ympApiAction.setTitle(this.getTitile(ympApiAction));
                ympApiAction.setRespbody(this.getRespbody(ympApiAction));
                ympApiAction.setParams(this.getParams(ympApiAction));
                ympApiAction.setRespParam(this.getResp(ympApiAction));
                ympApiAction.setReturnObj(this.getRespObjObjs(ympApiAction));
                ympApiAction.setReturnDesc(this.getReturnDesc(ympApiAction));
                ympApiAction.setParamObjs(this.getParamObjs(ympApiAction));

                apiModule.getApiActions().set(i, ympApiAction);
            }
        }

        return apiModules;
    }

    /**
     * 获取@title上的信息
     */
    protected String getTitile(ApiAction aa) {
        DocTag titleTag = TagUtils.findTag(aa.getDocTags(), "@title");
        if (titleTag != null) {
            return (String) titleTag.getValues();
        } else {
            return aa.getComment();
        }
    }

    /**
     * 获取@respbody上的信息
     */
    protected String getRespbody(ApiAction aa) {
        DocTag respbodyTag = TagUtils.findTag(aa.getDocTags(), "@respbody");
        if (respbodyTag != null) {
            return (String) respbodyTag.getValues();
        }
        return null;

    }


    /**
     * 获取@param注释上的信息
     */
    protected List<ParamInfo> getParams(ApiAction aa) {
        List tags = TagUtils.findTags(aa.getDocTags(), "@param");
        List<ParamInfo> paramInfos = new ArrayList<>(tags.size());
        for (Object tag : tags) {
            ParamTagImpl paramTag = (ParamTagImpl) tag;
            ParamInfo paramInfo = new ParamInfo();
            paramInfo.setParamName(paramTag.getParamName());
            paramInfo.setParamDesc(paramTag.getParamDesc());
            paramInfo.setParamType(paramTag.getParamType());
            paramInfo.setDemoValue(paramTag.getDemoValue());
            paramInfo.setRequire(paramTag.isRequire());
            paramInfos.add(paramInfo);
        }
        return paramInfos;
    }

    /**
     * 获取@resp注释上的信息
     */
    protected List<ParamInfo> getResp(ApiAction aa) {
        List<DocTag> tags = TagUtils.findTags(aa.getDocTags(), "@resp");
        List<ParamInfo> list = new ArrayList(tags.size());
        for (DocTag tag : tags) {
            RespTagImpl respTag = (RespTagImpl) tag;
            ParamInfo paramInfo = new ParamInfo();
            paramInfo.setParamName(respTag.getParamName());
            paramInfo.setRequire(respTag.isRequire());
            paramInfo.setParamDesc(respTag.getParamDesc());
            paramInfo.setParamType(respTag.getParamType());
            paramInfo.setDemoValue(respTag.getDemoValue());
            paramInfo.setDataKey(respTag.getParamName());
            paramInfo.setDataType(paramInfo.getParamType());
            list.add(paramInfo);
        }
        return list;
    }

    /**
     * 获取@return注释上的描述语
     */
    protected String getReturnDesc(ApiAction aa) {
        DocTag tag = TagUtils.findTag(aa.getDocTags(), "@return");
        return tag != null ? tag.getValues().toString() : null;
    }

    /**
     * 获取@see注释上的对象
     */
    protected ObjectInfo getSeeObj(ApiAction aa) {
        SeeTagImpl tag = (SeeTagImpl) TagUtils.findTag(aa.getDocTags(), "@see");
        return tag != null ? tag.getValues() : null;
    }

    /**
     * 获取@paramObj注解上的对象
     */
    private List<ObjectInfo> getParamObjs(AbstractApiAction aa) {
        List<DocTag> tags = TagUtils.findTags(aa.getDocTags(), "@paramObj");
        List<ObjectInfo> paramObjs = new ArrayList<>(tags.size());
        for (DocTag tag : tags) {
            ParamObjTagImpl paramObjTag = (ParamObjTagImpl) tag;
            paramObjs.add(paramObjTag.getValues());
        }
        return paramObjs;
    }

    /**
     * 获取@respObj注解上的对象
     */
    private List<ObjectInfo> getRespObjObjs(AbstractApiAction aa) {
        List<DocTag> tags = TagUtils.findTags(aa.getDocTags(), "@respObj");
        List<ObjectInfo> paramObjs = new ArrayList<>(tags.size());
        for (DocTag tag : tags) {
            RespObjTagImpl paramObjTag = (RespObjTagImpl) tag;
            paramObjs.add(paramObjTag.getValues());
        }
        return paramObjs;
    }
}
