package com.mx.dev.support.jwt;

/**
 * @Author: mengxiang.
 * @Date: 2020/12/1.
 * @Time: 11:09 上午.
 * @Description:
 */
public class JwtBean implements java.io.Serializable {

    private String token;

    private long verifyTime;

    private JwtBean() {

    }

    public JwtBean(String token, long verifyTime) {
        this.token = token;
        this.verifyTime = verifyTime;
    }

    public static JwtBean builder() {
        return new JwtBean();
    }

    public JwtBean token(String token) {
        this.token = token;
        return this;
    }

    public String token() {
        return this.token;
    }

    public JwtBean verifyTime(long verifyTime) {
        this.verifyTime = verifyTime;
        return this;
    }

    public long verifyTime() {
        return this.verifyTime;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(long verifyTime) {
        this.verifyTime = verifyTime;
    }
}
