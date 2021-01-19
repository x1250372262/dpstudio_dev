package com.dpstudio.dev.support.jwt;

/**
 * @Author: mengxiang.
 * @Date: 2020/06/13.
 * @Time: 20:20 .
 * @Description: jwt专用配置
 */
public class JwtConfig {

    // 有效期，单位豪秒， 默认0  永久有效
    private long verifyTime = 0L;
    //密钥
    private String secret = "dpstudioJwt";
    //header参数名
    private String headerName = "dpstudioJwt";
    //参数名
    private String paramName = "dpstudioJwt";
    //是否自动设置到response
    private boolean autoResponse = true;

    private JwtConfig() {
    }

    public static JwtConfig builder() {
        return new JwtConfig();
    }

    public JwtConfig verifyTime(long verifyTime) {
        this.verifyTime = verifyTime;
        return this;
    }

    public long verifyTime() {
        return this.verifyTime;
    }

    public JwtConfig secret(String secret) {
        this.secret = secret;
        return this;
    }

    public String secret() {
        return this.secret;
    }

    public JwtConfig headerName(String headerName) {
        this.headerName = headerName;
        return this;
    }

    public String headerName() {
        return this.headerName;
    }

    public JwtConfig paramName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    public String paramName() {
        return this.paramName;
    }

    public JwtConfig autoResponse(boolean autoResponse) {
        this.autoResponse = autoResponse;
        return this;
    }

    public boolean autoResponse() {
        return this.autoResponse;
    }


    public long getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(long verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public boolean isAutoResponse() {
        return autoResponse;
    }

    public void setAutoResponse(boolean autoResponse) {
        this.autoResponse = autoResponse;
    }
}
