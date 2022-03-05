package dev.yukikaze.portfolio.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import dev.yukikaze.portfolio.AppProperties;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.objects.JwtPayload;

/**
 * JWTに関するユーティリティー
 */
@Component
public class JwtUtils {
    /**
     * 有効期限
     */
    private final Long expire;

    /**
     * アルゴリズム
     */
    private final Algorithm algorithm;

    /**
     * コンストラクター
     *
     * @param appProperties アプリケーション設定
     */
    public JwtUtils(AppProperties appProperties) {
        this.expire = appProperties.getJwt().getExp() * 24 * 60 * 60 * 1000;
        this.algorithm = Algorithm.HMAC256(appProperties.getJwt().getSecret());
    }

    /**
     * JWTトークンの生成
     *
     * @param id         payloadに含めるユーザーID
     * @param permission payloadに含めるユーザー権限
     *
     * @return JWTトークン
     */
    public String generateToken(long id, UsersPermission permission) {
        var expireTime = new Date(System.currentTimeMillis() + this.expire);
        String token = JWT.create().withExpiresAt(expireTime).withClaim("id", id)
                .withClaim("permission", permission.toString()).sign(this.algorithm);

        return token;
    }

    /**
     * JWTトークンの検証
     *
     * @param token JWTトークン
     *
     * @return Payloadオブジェクト
     */
    public Optional<JwtPayload> verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(this.algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            byte[] byteData = Base64.getDecoder().decode(jwt.getPayload());
            String data = new String(byteData, StandardCharsets.UTF_8);

            var mapper = new ObjectMapper();
            JwtPayload payload = mapper.readValue(data, JwtPayload.class);

            return Optional.ofNullable(payload);
        } catch (Exception exception) {
            return null;
        }
    }
}
