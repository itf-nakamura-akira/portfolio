package dev.yukikaze.portfolio.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import dev.yukikaze.portfolio.AppProperties;
import dev.yukikaze.portfolio.controllers.LoginController.LoginRequestBody;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.mappers.UsersMapper;
import dev.yukikaze.portfolio.mappers.UsersMapperMock;
import dev.yukikaze.portfolio.objects.JwtPayload;
import dev.yukikaze.portfolio.services.AuthenticationService;
import dev.yukikaze.portfolio.utils.CookieUtils;
import dev.yukikaze.portfolio.utils.CookieUtilsTest;
import dev.yukikaze.portfolio.utils.JwtUtils;

/**
 * LoginController のテストコード
 */
@DisplayName("LoginController のテストコード")
public class LoginControllerTest {
    /**
     * AppProperties
     */
    private AppProperties appProperties;

    /**
     * JwtUtils
     */
    private JwtUtils jwtUtils;

    /**
     * LoginController
     */
    private LoginController loginController;

    /**
     * UsersMapper(assert用)
     */
    private UsersMapper usersMapper;

    /**
     * 初期化
     */
    @BeforeEach
    public void beforeEach() {
        this.usersMapper = new UsersMapperMock();
        this.appProperties = new AppProperties();
        var jwtProperties = new AppProperties.Jwt();
        jwtProperties.setExp("7");
        jwtProperties.setSecret("secret");
        this.appProperties.setJwt(jwtProperties);
        var cookieProperties = new AppProperties.Cookie();
        cookieProperties.setDomain("localhost");
        cookieProperties.setSecure("true");
        this.appProperties.setCookie(cookieProperties);
        this.jwtUtils = new JwtUtils(this.appProperties);
        var cookieUtils = new CookieUtils(this.appProperties, this.jwtUtils);
        var authenticationService = new AuthenticationService(this.usersMapper);
        this.loginController = new LoginController(cookieUtils, authenticationService);
    }

    @Test
    @DisplayName("login メソッドのテスト")
    public void login() {
        // 正しい認証情報による認証
        var body = new LoginRequestBody("admin", "admin");
        var response = new Response();
        response.setCoyoteResponse(new org.apache.coyote.Response());

        this.loginController.login(body, response);

        // Cookieの確認(Admin)
        String jwtStr = CookieUtilsTest.cookieStrFromCookie(response, "jwt");
        Optional<JwtPayload> payload = this.jwtUtils.verifyToken(jwtStr);

        assertTrue(payload.isPresent());
        assertEquals(payload.get().id(), 1L);
        assertEquals(payload.get().permission(), UsersPermission.Admin);

        Date tokenExp = CookieUtilsTest.getExpiresFromCookie(response);
        var afterExp = new Date(
                System.currentTimeMillis() + this.appProperties.getJwt().getExp() * 24 * 60 * 60 * 1000);
        long sub = Math.abs(afterExp.getTime() - tokenExp.getTime());

        assertTrue(sub < 5 * 1000); // 差が5秒以下

        // 正しい認証情報による認証
        body = new LoginRequestBody("imadu", "imadu");
        response = new Response();
        response.setCoyoteResponse(new org.apache.coyote.Response());

        this.loginController.login(body, response);

        // Cookieの確認(User)
        jwtStr = CookieUtilsTest.cookieStrFromCookie(response, "jwt");
        payload = this.jwtUtils.verifyToken(jwtStr);

        assertTrue(payload.isPresent());
        assertEquals(payload.get().permission(), UsersPermission.User);

        // 正しい認証情報による認証
        body = new LoginRequestBody("referencer", "referencer");
        response = new Response();
        response.setCoyoteResponse(new org.apache.coyote.Response());

        this.loginController.login(body, response);

        // Cookieの確認(User)
        jwtStr = CookieUtilsTest.cookieStrFromCookie(response, "jwt");
        payload = this.jwtUtils.verifyToken(jwtStr);

        assertTrue(payload.isPresent());
        assertEquals(payload.get().permission(), UsersPermission.Referencer);

        // 間違った認証情報による認証(存在しないユーザー)
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> this.loginController.login(new LoginRequestBody("invalid", "referencer"), new Response()));

        assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
        assertEquals("ログインに失敗しました。", e.getReason());

        // 間違った認証情報による認証(間違ったパスワード)
        e = assertThrows(ResponseStatusException.class,
                () -> this.loginController.login(new LoginRequestBody("referencer", "invalid"), new Response()));

        assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
        assertEquals("ログインに失敗しました。", e.getReason());

        // 無効なユーザーの認証情報による認証
        e = assertThrows(ResponseStatusException.class,
                () -> this.loginController.login(new LoginRequestBody("ichinohe", "ichinohe"), new Response()));

        assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
        assertEquals("ログインに失敗しました。", e.getReason());
    }
}
