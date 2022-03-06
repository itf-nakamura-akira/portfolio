package dev.yukikaze.portfolio.utils;

import java.time.Duration;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import dev.yukikaze.portfolio.AppProperties;
import dev.yukikaze.portfolio.Constants;
import dev.yukikaze.portfolio.enums.UsersPermission;

/**
 * Cookieに関するユーティリティー
 */
@Component
public class CookieUtils {
    /**
     * AppProperties
     */
    private final AppProperties appProperties;

    /**
     * JwtUtils
     */
    private final JwtUtils jwtUtils;

    /**
     * コンストラクター
     *
     * @param appProperties AppProperties
     * @param jwtUtils      JwtUtils
     */
    public CookieUtils(AppProperties appProperties, JwtUtils jwtUtils) {
        this.appProperties = appProperties;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Cookieをセットする
     *
     * @param response   レスポンスオブジェクト
     * @param id         CookieにセットするユーザーID
     * @param permission Cookieにセットするユーザー権限
     */
    public void setCookie(HttpServletResponse response, long id, UsersPermission permission) {
        String jwt = this.jwtUtils.generateToken(id, permission);
        ResponseCookie cookie = this.generateCookie(Constants.JWT_COOKIE_KEY, jwt,
                this.appProperties.getJwt().getExp());
        response.addHeader("Set-Cookie", cookie.toString());
    }

    /**
     * Cookieを削除する
     *
     * @param response レスポンスオブジェクト
     */
    public void delCookie(HttpServletResponse response) {
        ResponseCookie cookie = this.generateCookie(Constants.JWT_COOKIE_KEY, null, 0);
        response.setHeader("Set-Cookie", cookie.toString());
    }

    /**
     * Cookieを生成する
     *
     * @param name  Cookieのキー
     * @param value Coookieの値
     * @param exp   Coookieの有効期限
     *
     * @return Cookie
     */
    private ResponseCookie generateCookie(String name, String value, long exp) {
        return ResponseCookie.from(name, value).domain(this.appProperties.getCookie().getDomain())
                .maxAge(Duration.ofDays(exp)).httpOnly(true)
                .secure(this.appProperties.getCookie().isSecure()).path("/")
                .sameSite(SameSiteCookies.STRICT.getValue()).build();
    }
}
