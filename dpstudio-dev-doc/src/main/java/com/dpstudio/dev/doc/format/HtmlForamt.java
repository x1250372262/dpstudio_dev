package com.dpstudio.dev.doc.format;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dpstudio.dev.doc.IDoc;
import com.dpstudio.dev.doc.bean.ApiResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 徐建鹏
 * @Date 2018/08/09.
 * @Time: 14:00.
 * @Description:
 */
public class HtmlForamt implements Format {

    @Override
    public String format(ApiResult apiResult) {
        InputStream in = HtmlForamt.class.getResourceAsStream("html.vm");
        if (in != null) {
            try {
                String s = IOUtils.toString(in, IDoc.CHARSET);

                Map<String, Object> model = new HashMap<String, Object>();
                model.put("title", StringUtils.defaultString((String) apiResult.getProperties().get("title"), "梦祥文档"));
                model.put("version", StringUtils.defaultString((String) apiResult.getProperties().get("version"), "1.0.0"));
                model.put("apiHost", StringUtils.defaultString((String) apiResult.getProperties().get("host"), ""));
                model.put("apiInfoList", apiResult.getApiInfoList());
                return s.replace("_apis_json", JSON.toJSONString(model, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.PrettyFormat));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
        return "";
    }
}
