package dev.yukikaze.portfolio.controllers.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.servlet.http.Cookie;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.yukikaze.portfolio.Constants;
import dev.yukikaze.portfolio.enums.UsersPermission;
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
        this.appController = new AppController();
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
}
