package com.mx.dev.test;

import net.ymate.platform.commons.http.HttpClientHelper;
import net.ymate.platform.commons.http.IHttpResponse;
import net.ymate.platform.commons.lang.BlurObject;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mengxiang.
 * @create: 2021-01-06 15:43
 * @Description:
 */
public class MVC {

    private static int port;

    private static final String HOST_NAME;

    private String path;

    private final Map<String, String> params = new HashMap<>();

    static {
        HOST_NAME = "127.0.0.1";
        try {
            ServerSocket s = new ServerSocket(0);
            port = s.getLocalPort();
        } catch (IOException e) {
            port = 8080;
            e.printStackTrace();
        }
    }

    public static MVC create() {
        return new MVC();
    }


    public static int port() {
        return port;
    }

    public static String hostName() {
        return HOST_NAME;
    }

    public static String baseUrl() {
        return "http://".concat(HOST_NAME).concat(":").concat(BlurObject.bind(port).toStringValue()).concat("/");
    }

    public MVC path(String path) {
        this.path = path;
        return this;
    }

    public MVC param(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    public MVC params(Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    public MvcResult httpGet() {
        MvcResult mvcResult = MvcResult.create();
        try {
            IHttpResponse iHttpResponse = HttpClientHelper.create().get(baseUrl() + path, this.params);
            if(iHttpResponse == null){
                throw new NullArgumentException("iHttpResponse");
            }
            mvcResult.httpCode(iHttpResponse.getStatusCode()).httpMsg(iHttpResponse.getReasonPhrase());
            if(200==iHttpResponse.getStatusCode()){
                String result = iHttpResponse.getContent();
                if(StringUtils.isNotBlank(result)){
                    mvcResult.content(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mvcResult;
    }

    public MvcResult httpPost() {
        MvcResult mvcResult = MvcResult.create();
        try {
            String result = HttpClientHelper.create().post(baseUrl() + path, this.params).getContent();
            if (result != null) {
                mvcResult.content(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mvcResult;
    }
}
