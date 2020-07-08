package com.dpstudio.dev.doc.format;

import com.dpstudio.dev.doc.bean.*;
import net.ymate.platform.commons.json.JsonWrapper;
import net.ymate.platform.log.Logs;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author mengxiang
 * @Date 2018/08/09.
 * @Time: 14:00.
 * @Description:
 */
public class MarkdownFormat implements Format {


    private static final VelocityTemplater VELOCITY_TEMPLATER = new VelocityTemplater("com/dpstudio/dev/doc/format/markdown.vm");

    @Override
    public String format(ApiResult apiResult) {
        StringBuilder sb = new StringBuilder();
        for (ApiModule apiModule : apiResult.getApiModuleList()) {
            sb.append(format(apiModule)).append("\n\n");
        }
        return sb.toString();
    }

    private String format(ApiModule apiModule) {

        apiModule.getApiActions().forEach(apiAction -> {
            if (StringUtils.isNotBlank(apiAction.getRespbody())) {
                apiAction.setRespbody(JsonWrapper.fromJson(apiAction.getRespbody()).getAsJsonObject().toString(false,true));
            }
            if (!apiAction.getParamObjs().isEmpty()) {
                apiAction.getParamObjs().forEach(objectInfo -> {
                    objectInfo.getFieldInfos().forEach(fieldInfo -> {
                        ParamInfo paramInfo = toParamInfo(fieldInfo);
                        apiAction.getParams().add(paramInfo);
                    });
                });
            }
            if (!apiAction.getReturnObj().isEmpty()) {
                apiAction.getReturnObj().forEach(objectInfo -> {
                    objectInfo.getFieldInfos().forEach(fieldInfo -> {
                        RespInfo respInfo = toRespInfo(fieldInfo);
                        respInfo.setDataKey(objectInfo.getDataKey());
                        respInfo.setDataType(objectInfo.getDataType());
                        apiAction.getRespParam().add(respInfo);
                    });
                });
            }
        });
        try {
            Map<String, Object> map = PropertyUtils.describe(apiModule);
            return VELOCITY_TEMPLATER.parse(map);
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
        param.setDemoValue(fieldInfo.getDemoValue());
        return param;
    }

    private RespInfo toRespInfo(FieldInfo fieldInfo) {
        RespInfo respInfo = new RespInfo();
        respInfo.setParamType(fieldInfo.getSimpleTypeName());
        respInfo.setParamDesc(fieldInfo.getComment());
        respInfo.setParamName(fieldInfo.getName());
        respInfo.setRequire(fieldInfo.isRequire());
        respInfo.setDemoValue(fieldInfo.getDemoValue());
        return respInfo;
    }

    public static String formatJson(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        char last;
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}
