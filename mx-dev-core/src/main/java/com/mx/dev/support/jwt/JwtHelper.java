package com.mx.dev.support.jwt;


import com.alibaba.fastjson.JSONObject;
import com.mx.dev.core.R;
import com.mx.dev.code.C;
import io.jsonwebtoken.*;
import net.ymate.platform.commons.json.JsonWrapper;
import net.ymate.platform.commons.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mengxiang
 * @Date 2020.06.08.
 * @Time: 16:00 .
 * @Description:
 */
public class JwtHelper {

    private Map<String, Object> attrs = new HashMap<>();

    private static JwtConfig JWT_CONFIG = JwtConfig.builder();

    private static final JwtHelper JWT_HELPER_TARGET = new JwtHelper();

    private JwtHelper() {
    }

    public static JwtHelper get(JwtConfig jwtConfig) {
        JWT_CONFIG = jwtConfig;
        return JWT_HELPER_TARGET;
    }

    public static JwtHelper get() {
        return JWT_HELPER_TARGET;
    }


    public static R parse(String token) {
        SecretKey secretKey = generalKey();
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();

            String jsonString = claims.getSubject();

            if (StringUtils.isBlank(jsonString)) {
                return null;
            }
            return R.ok().attrs(JsonWrapper.fromJson(jsonString).getAsJsonObject().toMap());

        } catch (MalformedJwtException e) {
            return R.create(C.JWT_ERROR.getCode()).msg(C.JWT_ERROR.getMsg());
        } catch (ExpiredJwtException e) {
            return R.create(C.JWT_OUT_TIME.getCode()).msg(C.JWT_OUT_TIME.getMsg());
        }
    }

    public JwtBean create() {
        if (attrs.isEmpty()) {
            throw new RuntimeException("请设置jwt参数");
        }
        SecretKey secretKey = generalKey();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long currentTimeMillis = DateTimeUtils.currentTimeMillis();
        Date now = new Date(currentTimeMillis);

        this.attrs.put("isuuedAt", currentTimeMillis);
        String subject = JSONObject.toJSONString(this.attrs);

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, secretKey);

        long time = 0;
        if (JWT_CONFIG.verifyTime() > 0) {
            time = currentTimeMillis + JWT_CONFIG.verifyTime();
            builder.setExpiration(new Date(time));
        }
        return new JwtBean(builder.compact(), time);
    }

    public JwtHelper attr(String key, String value) {
        this.attrs.put(key, value);
        return this;
    }

    public JwtHelper attrs(Map<String, Object> attrs) {
        this.attrs = attrs;
        return this;
    }


    private static SecretKey generalKey() {
        byte[] encodedKey = DatatypeConverter.parseBase64Binary(JWT_CONFIG.secret());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }


}
