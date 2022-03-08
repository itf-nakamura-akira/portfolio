package dev.yukikaze.portfolio.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import dev.yukikaze.portfolio.AppProperties;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.objects.JwtPayload;

/**
 * JwtUtils のテストコード
 */
@DisplayName("JwtUtils のテストコード")
public class JwtUtilsTest {
    @Test
    @DisplayName("generateToken メソッドのテスト")
    public void generateToken() {
        JwtUtils jwtUtils = generateJwtUtils("7", "asdf1234");
        String token;
        JwtPayload jwtPayload;

        // トークンが生成されているか
        token = jwtUtils.generateToken(1L, UsersPermission.Admin);

        assertTrue(StringUtils.hasText(token));

        // 生成したトークンの中身が正しいか
        jwtPayload = jwtUtils.verifyToken(token).get();

        assertEquals(jwtPayload.id(), 1L);
        assertEquals(jwtPayload.permission(), UsersPermission.Admin);

        var tokenExp = new Date(jwtPayload.exp() * 1000);
        var after7Days = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
        var sub = Math.abs(after7Days.getTime() - tokenExp.getTime());

        assertTrue(sub < 5 * 1000); // 差が5秒以下

        // トークンが生成されているか
        token = jwtUtils.generateToken(5L, UsersPermission.User);

        assertTrue(StringUtils.hasText(token));

        // 生成したトークンの中身が正しいか
        jwtPayload = jwtUtils.verifyToken(token).get();

        assertEquals(jwtPayload.id(), 5L);
        assertEquals(jwtPayload.permission(), UsersPermission.User);

        tokenExp = new Date(jwtPayload.exp() * 1000);
        after7Days = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
        sub = Math.abs(after7Days.getTime() - tokenExp.getTime());

        assertTrue(sub < 5 * 1000); // 差が5秒以下
    }

    @Test
    @DisplayName("verifyToken メソッドのテスト")
    public void verifyToken() {
        JwtUtils jwtUtils = generateJwtUtils("7", "asdf1234");
        String token;
        Optional<JwtPayload> jwtPayload;

        // 期限が有効なトークンが検証に成功するか
        token = jwtUtils.generateToken(1L, UsersPermission.Admin);
        jwtPayload = jwtUtils.verifyToken(token);

        assertTrue(jwtPayload.isPresent());

        // 期限が過ぎたトークンが検証に失敗するか
        jwtUtils = generateJwtUtils("-1", "asdf1234");
        token = jwtUtils.generateToken(1L, UsersPermission.Admin);
        jwtPayload = jwtUtils.verifyToken(token);

        assertTrue(jwtPayload.isEmpty());

        // 改ざんされたトークンが検証に失敗するか(ユーザー権限の改ざん)
        jwtUtils = generateJwtUtils("7", "asdf1234");
        token = jwtUtils.generateToken(3L, UsersPermission.User);
        String[] splitedToken = token.split(Pattern.quote("."));

        byte[] decoded = Base64.getDecoder().decode(splitedToken[1]);
        String decodedMsg = new String(decoded);
        decodedMsg = decodedMsg.replace("\"permission\":\"User\"", "\"permission\":\"Admin\"");

        byte[] bytes = decodedMsg.getBytes(StandardCharsets.UTF_8);
        Encoder encoder = Base64.getEncoder();
        String encoded = encoder.encodeToString(bytes);

        jwtPayload = jwtUtils.verifyToken(splitedToken[0] + "." + encoded + "." + splitedToken[2]);

        assertTrue(jwtPayload.isEmpty());

        // 改ざんされたトークンが検証に失敗するか(ユーザーIDの改ざん)
        jwtUtils = generateJwtUtils("7", "asdf1234");
        token = jwtUtils.generateToken(3L, UsersPermission.User);
        splitedToken = token.split(Pattern.quote("."));

        decoded = Base64.getDecoder().decode(splitedToken[1]);
        decodedMsg = new String(decoded);
        decodedMsg = decodedMsg.replace("\"id\":3", "\"id\":1");

        bytes = decodedMsg.getBytes(StandardCharsets.UTF_8);
        encoder = Base64.getEncoder();
        encoded = encoder.encodeToString(bytes);

        jwtPayload = jwtUtils.verifyToken(splitedToken[0] + "." + encoded + "." + splitedToken[2]);

        assertTrue(jwtPayload.isEmpty());

        // 改ざんされたトークンが検証に失敗するか(有効期限の改ざん)
        jwtUtils = generateJwtUtils("7", "asdf1234");
        token = jwtUtils.generateToken(3L, UsersPermission.User);
        splitedToken = token.split(Pattern.quote("."));

        decoded = Base64.getDecoder().decode(splitedToken[1]);
        decodedMsg = new String(decoded);
        decodedMsg = "\"permission\":\"User\",\"id\":3,\"exp\":9999999999";

        bytes = decodedMsg.getBytes(StandardCharsets.UTF_8);
        encoder = Base64.getEncoder();
        encoded = encoder.encodeToString(bytes);

        jwtPayload = jwtUtils.verifyToken(splitedToken[0] + "." + encoded + "." + splitedToken[2]);

        assertTrue(jwtPayload.isEmpty());
    }

    /**
     * テスト用のJwtUtilsを生成する
     *
     * @param exp    有効期限(日)
     * @param secret シークレットキー
     *
     * @return JwtUtils
     */
    public static JwtUtils generateJwtUtils(String exp, String secret) {
        var appProperties = new AppProperties();
        var jwtProperties = new AppProperties.Jwt();
        jwtProperties.setExp(exp);
        jwtProperties.setSecret(secret);
        appProperties.setJwt(jwtProperties);
        JwtUtils jwtUtils = new JwtUtils(appProperties);

        return jwtUtils;
    }
}
