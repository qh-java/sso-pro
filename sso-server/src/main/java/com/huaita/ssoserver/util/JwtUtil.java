package com.huaita.ssoserver.util;


import com.huaita.ssoserver.bean.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    //
    public static final String SUBJECT = "dapanger";

    //过期时长
    public static final long EXPIRE = 1000 * 60 * 60;

    //秘钥
    public static final String APPSECRET = "66666666";


    /**
     * 加密并获取token
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user) {

        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", user.getId())  //token存储信息
                .claim("name", user.getName()) //token存储信息
                .setIssuedAt(new Date())  //创建时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))  // 过期时间
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
        ;
        return token;
    }

    /**
     * 校验token
     * @param token
     * @return  是否为null 以判断token时候有效
     */
    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJwt(token).getBody();
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

}
