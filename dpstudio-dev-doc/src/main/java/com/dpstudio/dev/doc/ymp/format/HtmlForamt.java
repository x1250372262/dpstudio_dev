package com.dpstudio.dev.doc.ymp.format;

import com.alibaba.fastjson.JSON;
import com.dpstudio.dev.doc.core.format.Format;
import com.dpstudio.dev.doc.core.model.ApiDoc;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

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
    public String format(ApiDoc apiDoc) {
        InputStream in = HtmlForamt.class.getResourceAsStream("html.vm");
        if (in != null) {
            try {
                String s = IOUtils.toString(in, "utf-8");

                Map<String, Object> model = new HashMap<String, Object>();
                model.put("title", StringUtils.defaultString((String) apiDoc.getProperties().get("title"), "接口文档"));
                model.put("apiModules", apiDoc.getApiModules());
                return s.replace("_apis_json", JSON.toJSONString(model));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
        return "";
    }
}
