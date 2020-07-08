package com.dpstudio.dev.support.jwt;


import com.alibaba.fastjson.JSONObject;
import com.dpstudio.dev.core.R;
import io.jsonwebtoken.*;
import net.ymate.platform.commons.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Map;

/**
 * @author mengxiang
 * @Date 2020.06.08.
 * @Time: 16:00 .
 * @Description:
 */
public class JwtHelper {

    private static JwtConfig jwtConfigTarget;

    private static final JwtHelper JWT_HELPER_TARGET = new JwtHelper();

    private JwtHelper() {
    }

    public static JwtHelper get(JwtConfig jwtConfig) {
        jwtConfigTarget = jwtConfig;
        return JWT_HELPER_TARGET;
    }



    public R parse(String token) {
        SecretKey secretKey = generalKey();
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();

            String jsonString = claims.getSubject();

            if (StringUtils.isBlank(jsonString)) {
                return null;
            }
            return R.ok().attrs(JSONObject.parseObject(jsonString));

        } catch (MalformedJwtException e) {
            return R.create(-551).msg("jwt 签名错误或解析错误");
        } catch (ExpiredJwtException e) {
            return R.create(-552).msg("jwt 已经过期");
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

        if (jwtConfigTarget.verifyTime() > 0) {
            long time = nowMillis + jwtConfigTarget.verifyTime();
            builder.setExpiration(new Date(time));
        }

        return builder.compact();
    }


    private SecretKey generalKey() {
        byte[] encodedKey = DatatypeConverter.parseBase64Binary(jwtConfigTarget.secret());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }


}
