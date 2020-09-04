package com.dpstudio.dev.doc.bean;

import java.util.List;

/**
 * @Author: xujianpeng.
 * @Date: 2020/7/9.
 * @Time: 下午3:58.
 * @Description:
 */
public class SdkInfo {

    private String comment;

    private String name;

    private List<ApiInfo> apiInfos;

    public static class ApiInfo {

        private String title;

        private String methodName;

        private String method;

        private String uri;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApiInfo> getApiInfos() {
        return apiInfos;
    }

    public void setApiInfos(List<ApiInfo> apiInfos) {
        this.apiInfos = apiInfos;
    }
}
