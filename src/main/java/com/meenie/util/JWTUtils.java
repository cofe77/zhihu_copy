package com.meenie.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Map;

@Slf4j
public class JWTUtils {
    private static final String SING = "meenie";

    public static String getToken(Map<String,String> map){
        //获取日历对象
        Calendar calendar = Calendar.getInstance();
        //默认7天过期
        calendar.add(Calendar.DATE,7);
        //新建一个JWT的Builder对象
        JWTCreator.Builder builder = JWT.create();
        //将map集合中的数据设置进payload
        map.forEach(builder::withClaim);
        //设置过期时间和签名
        log.info("时间={}"+calendar.getTime());
        return builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(SING));
    }

    public static DecodedJWT getTokenInfo(String token){
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }
}
