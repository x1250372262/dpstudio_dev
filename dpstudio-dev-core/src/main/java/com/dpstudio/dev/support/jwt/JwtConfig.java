package com.dpstudio.dev.support.jwt;

public class JwtConfig {

    public static final String ISUUED_AT = "isuuedAt";

    /**
     * 密钥
     */
    private String secret = "dpstudio";

    /**
     * 有效期，单位秒，
     * 默认0  永久有效
     */
    private long verifyTime = 0L;

    public JwtConfig() {

    }

    public JwtConfig(String secret, long verifyTime) {
        this.secret = secret;
        this.verifyTime = verifyTime;
    }

    public static JwtConfig builder() {
        return new JwtConfig();
    }

    public JwtConfig secret(String secret) {
        this.secret = secret;
        return this;
    }

    public String secret() {
        return this.secret;
    }

    public JwtConfig verifyTime(long verifyTime) {
        this.verifyTime = verifyTime;
        return this;
    }

    public long verifyTime() {
        return this.verifyTime;
    }
}
