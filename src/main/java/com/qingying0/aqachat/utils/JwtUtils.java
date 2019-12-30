package com.qingying0.aqachat.utils;


import com.qingying0.aqachat.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtils {

    private static String key = "somethinghereshouldbelongwehniaodhnsio";

    private static byte[] keyBytes = key.getBytes();
    private static SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);


    public static String getToken(String id, String sub) {
        JwtBuilder builder= Jwts.builder()
                .setId(id)
                .setSubject(sub)
                .setIssuedAt(new Date())//设置签发时间
                .signWith(secretKey);//设置签名秘钥
        return builder.compact();
    }

    public static Claims parseToken(String jws) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jws)
                .getBody();
    }

    public static User parseToUser(String jws) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jws)
                .getBody();
        User user = new User();
        user.setId(Long.valueOf(claims.getId()));
        user.setUsername(claims.getSubject());
        return user;
    }

}
