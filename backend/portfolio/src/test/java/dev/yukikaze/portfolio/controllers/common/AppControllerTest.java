package dev.yukikaze.portfolio.controllers.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.Cookie;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.yukikaze.portfolio.AppProperties;
import dev.yukikaze.portfolio.Constants;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.objects.JwtPayload;
import dev.yukikaze.portfolio.utils.CookieUtils;
import dev.yukikaze.portfolio.utils.CookieUtilsTest;
import dev.yukikaze.portfolio.utils.JwtUtils;
import dev.yukikaze.portfolio.utils.JwtUtilsTest;

/**
 * AppController のテストコード
 */
@DisplayName("AppController のテストコード")
public class AppControllerTest {
    /**
     * AppController
     */
    private AppController appController;

    /**
     * 初期化
     */
    @BeforeEach
    public void beforeEach() {
        var appProperties = new AppProperties();
        var jwtProperties = new AppProperties.Jwt();
        jwtProperties.setExp("7");
        jwtProperties.setSecret("secret");
        appProperties.setJwt(jwtProperties);
        var cookieProperties = new AppProperties.Cookie();
        cookieProperties.setDomain("localhost");
        cookieProperties.setSecure("true");
        appProperties.setCookie(cookieProperties);
        var jwtUtils = new JwtUtils(appProperties);
        var cookieUtils = new CookieUtils(appProperties, jwtUtils);

        this.appController = new AppController(cookieUtils);
    }

    @Test
    @DisplayName("isAuthorized メソッドのテスト")
    public void isAuthorized() {
        // 未認証のリクエスト
        var request = new Request(new Connector());
        var response = this.appController.isAuthorized(request);

        assertFalse(response.isAuthorized());

        // 認証後のリクエスト
        JwtUtils jwtUtils = JwtUtilsTest.generateJwtUtils("7", "secret");
        String jwt = jwtUtils.generateToken(1L, UsersPermission.Admin);
        var jwtCookie = new Cookie(Constants.JWT_COOKIE_KEY, jwt);
        request = new Request(new Connector());
        request.addCookie(jwtCookie);
        response = this.appController.isAuthorized(request);

        assertTrue(response.isAuthorized());
    }

    @Test
    @DisplayName("logout メソッドのテスト")
    public void logout() {
        JwtUtils jwtUtils = JwtUtilsTest.generateJwtUtils("7", "secret");
        var response = new Response();
        response.setCoyoteResponse(new org.apache.coyote.Response());

        // 認証後のアクセス
        this.appController.logout(response);

        // Cookieの確認
        String jwtStr = CookieUtilsTest.cookieStrFromCookie(response, "jwt");
        Optional<JwtPayload> payload = jwtUtils.verifyToken(jwtStr);

        assertTrue(payload.isEmpty());

        Date tokenExp = CookieUtilsTest.getExpiresFromCookie(response);

        assertTrue(System.currentTimeMillis() - tokenExp.getTime() > 0);
    }
}
