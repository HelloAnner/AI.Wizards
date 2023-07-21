package com.fr.ai.wizards.common.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/21
 */
public class JWTGenerator {

    private final static int EXP_SECONDS = 3600;


    /**
     * https://open.bigmodel.cn/doc/api#nosdk 这里的 秘钥不足 256bit ，pyjwt 是可以生成的，但是这里不行
     * <p>
     * TODO 直接 RPC 调取安全服务获取token
     *
     * @return token
     */
    @Deprecated
    public static String nextGLMToken() {
        String[] keys = APIKeyPacks.getOpenglmAPIKey().split("\\.");
        return next(keys[0], keys[1]);
    }


    /**
     * JWT Token
     *
     * @param id     payload 内容
     * @param secret 加密秘钥，一般是 rsa 的私钥
     */
    private static String next(String id, String secret) {
        long expTime = System.currentTimeMillis() + EXP_SECONDS * 1000L;
        return Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("sign_type", "SIGN")
                .claim("api_key", id)
                .claim("exp", expTime)
                .claim("timestamp", System.currentTimeMillis())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
