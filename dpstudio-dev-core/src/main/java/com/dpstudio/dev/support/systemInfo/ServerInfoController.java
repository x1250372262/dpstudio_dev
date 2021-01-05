package com.dpstudio.dev.support.systemInfo;

import com.dpstudio.dev.core.R;
import com.dpstudio.dev.core.V;
import com.dpstudio.dev.support.systemInfo.bean.ServerInfo;
import net.ymate.platform.commons.http.HttpClientHelper;
import net.ymate.platform.commons.http.IHttpResponse;
import net.ymate.platform.commons.json.IJsonObjectWrapper;
import net.ymate.platform.commons.json.JsonWrapper;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.util.WebUtils;
import net.ymate.platform.webmvc.view.IView;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mengxiang.
 * @Date: 2020/11/21.
 * @Time: 3:59 下午.
 * @Description:
 */
@Controller
@RequestMapping("/dpstudio/server/info")
public class ServerInfoController {

    /**
     * 服务器信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/")
    public IView index(@RequestParam String host) throws Exception {
        String requestUri = WebUtils.buildUrl(WebContext.getRequest(), "/dpstudio/server/info", true);
        if (StringUtils.isNotBlank(host)) {
            requestUri = host + "/dpstudio/server/info";
        }
        Map<String, String> params = new HashMap<>();
        params.put("key", "dpstudio_mx_server_info");
        IHttpResponse response = HttpClientHelper.create().post(requestUri, params);
        if (response.getStatusCode() == 200) {
            JsonWrapper jsonWrapper = JsonWrapper.fromJson(response.getContent());
            IJsonObjectWrapper jsonObjectWrapper = jsonWrapper.getAsJsonObject();
            return V.view(R.create(jsonObjectWrapper.getInt("ret")).msg(jsonObjectWrapper.getString("msg")).attr("data",jsonObjectWrapper.get("serverInfo")));
        }
        return V.view(R.create(response.getStatusCode()).msg(response.getReasonPhrase()));
    }

    /**
     * 服务器信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = Type.HttpMethod.POST)
    public IView info(@VRequired(msg = "key不能为空")
                      @RequestParam String key) throws Exception {
        if (!"dpstudio_mx_server_info".equals(key)) {
            return V.view(R.fail().msg("key错误"));
        }
        ServerInfo serverInfo = SystemInfoUtils.serverInfo();
        return V.view(R.ok().attr("serverInfo", serverInfo));

    }

}
