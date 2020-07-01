package com.dpstudio.dev.doc.ymp.format;

import com.dpstudio.dev.doc.core.format.Format;
import com.dpstudio.dev.doc.core.model.*;
import com.dpstudio.dev.doc.core.utils.JsonUtils;
import net.ymate.platform.log.Logs;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 徐建鹏
 * @Date 2018/08/09.
 * @Time: 14:00.
 * @Description:
 */
public class MarkdownFormat implements Format {


    private VelocityTemplater templater = new VelocityTemplater("com/dpstudio/dev/doc/ymp/format/markdown.vm");

    @Override
    public String format(ApiResult apiResult) {
        StringBuilder sb = new StringBuilder();
        for (ApiModule apiModule : apiResult.getApiModuleList()) {
            sb.append(format(apiModule)).append("\n\n");
        }
        return sb.toString();
    }

    private String format(ApiModule apiModule) {

        for (ApiAction apiAction : apiModule.getApiActions()) {
            AbstractApiAction saa = (AbstractApiAction) apiAction;
            if (saa.isJson() && StringUtils.isNotBlank(saa.getRespbody())) {
                saa.setRespbody(JsonUtils.formatJson(saa.getRespbody()));
            }
            if (saa.getParamObjs().size() > 0) {
                //将@resp标签跟@return标签中重复的属性进行去重,以@resp的为准
                Set<String> paramNames = new HashSet<>();
                for (ParamInfo param : saa.getRespParam()) {
                    paramNames.add(param.getParamName());
                }

                for (ObjectInfo returnObj : saa.getReturnObj()) {
                    for (FieldInfo fieldInfo : returnObj.getFieldInfos()) {
                        if (paramNames.contains(fieldInfo.getName())) {
                            continue;
                        }
                        ParamInfo param = toParamInfo(fieldInfo);
                        saa.getRespParam().add(param);
                    }
                }

            }
            if (saa.getParamObjs().size() > 0) {
                //将@param跟@paramObj标签中重复的属性进行去重,以@param中的为准
                Set<String> paramNames = new HashSet<>();
                for (ParamInfo param : saa.getParams()) {
                    paramNames.add(param.getParamName());
                }

                for (ObjectInfo paramObj : saa.getParamObjs()) {
                    for (FieldInfo fieldInfo : paramObj.getFieldInfos()) {
                        if (paramNames.contains(fieldInfo.getName())) {
                            continue;
                        }
                        ParamInfo param = toParamInfo(fieldInfo);
                        saa.getParams().add(param);
                    }
                }
            }
        }
        try {
            Map<String, Object> map = PropertyUtils.describe(apiModule);
            return templater.parse(map);
        } catch (Exception e) {
            Logs.get().getLogger().error("输出markdown文档格式失败", e);
        }
        return null;
    }

    private ParamInfo toParamInfo(FieldInfo fieldInfo) {
        ParamInfo param = new ParamInfo();
        param.setParamType(fieldInfo.getSimpleTypeName());
        param.setParamDesc(fieldInfo.getComment());
        param.setParamName(fieldInfo.getName());
        param.setRequire(fieldInfo.isRequire());
        return param;
    }
}
