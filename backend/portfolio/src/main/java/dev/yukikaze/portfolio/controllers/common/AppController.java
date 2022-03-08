package dev.yukikaze.portfolio.controllers.common;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.yukikaze.portfolio.Constants;
import dev.yukikaze.portfolio.annotations.NonAuth;

/**
 * アプリケーション Controller
 */
@RestController
@RequestMapping("/common/app")
public class AppController {
    /**
     * 認証済みか確認する
     *
     * @param request リクエストデータ
     *
     * @return 認証済み情報
     */
    @GetMapping("isAuthorized")
    @NonAuth
    public IsAuthorizedResponse isAuthorized(HttpServletRequest request) {
        // JWTトークンのCookieを取得
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return new IsAuthorizedResponse(false);
        }

        Boolean hasJwtCookie = Arrays.stream(request.getCookies())
                .filter(v -> v.getName().equals(Constants.JWT_COOKIE_KEY))
                .findAny().isPresent();

        return new IsAuthorizedResponse(hasJwtCookie);
    }

    /**
     * isAuthorized メソッドのレスポンス
     */
    public record IsAuthorizedResponse(Boolean isAuthorized) {
    }
}
