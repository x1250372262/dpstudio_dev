package com.dpstudio.dev.support.jwt;


import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import net.ymate.platform.core.util.DateTimeUtils;
import org.apache.commons.lang.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Map;

/**
 * @author 徐建鹏
 * @Date 2020.06.08.
 * @Time: 16:00 .
 * @Description:
 */
public class JwtHelper {

    private static JwtConfig __jwtConfig;

    private static JwtHelper __jwtHelper = new JwtHelper();


    private JwtHelper() {
    }

    public static JwtHelper get(JwtConfig jwtConfig) {
        __jwtConfig = jwtConfig;
        return __jwtHelper;
    }



    public Map<String, Object> parse(String token) {
        SecretKey secretKey = generalKey();
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();

            String jsonString = claims.getSubject();

            if (StringUtils.isBlank(jsonString)) {
                return null;
            }
            return JSONObject.parseObject(jsonString);

        } catch (MalformedJwtException e) {
            throw new RuntimeException("jwt 签名错误或解析错误");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("jwt 已经过期");
        }
    }

    public String create(Map<String, Object> map) {

        SecretKey secretKey = generalKey();

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = DateTimeUtils.currentTimeMillis();
        Date now = new Date(nowMillis);

        map.put(JwtConfig.ISUUED_AT, nowMillis);
        String subject = JSONObject.toJSONString(map);

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, secretKey);

        if (__jwtConfig.verifyTime() > 0) {
            long time = nowMillis + __jwtConfig.verifyTime();
            builder.setExpiration(new Date(time));
        }

        return builder.compact();
    }


    private SecretKey generalKey() {
        byte[] encodedKey = DatatypeConverter.parseBase64Binary(__jwtConfig.secret());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }


}
