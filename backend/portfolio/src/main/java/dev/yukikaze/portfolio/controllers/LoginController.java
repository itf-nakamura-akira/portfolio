package dev.yukikaze.portfolio.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.yukikaze.portfolio.annotations.NonAuth;
import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.services.AuthenticationService;
import dev.yukikaze.portfolio.utils.CookieUtils;

/**
 * 認証 Controller
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    /**
     * CookieUtils
     */
    private final CookieUtils cookieUtils;

    /**
     * AuthorizationService
     */
    private final AuthenticationService authenticationService;

    /**
     * コンストラクター
     */
    public LoginController(CookieUtils cookieUtils,
            AuthenticationService authenticationService) {
        this.cookieUtils = cookieUtils;
        this.authenticationService = authenticationService;
    }

    /**
     * 認証処理
     *
     * @param body 認証情報
     */
    @NonAuth
    @PostMapping("signIn")
    public void signIn(@RequestBody SignInRequestBody body, HttpServletResponse response) {
        UsersEntity user = this.authenticationService.verify(body.account(), body.password()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ログインに失敗しました。"));

        // 認証Cookieのセット
        this.cookieUtils.setCookie(response, user.getId(), user.getPermission());
    }

    /**
     * signInメソッドのリクエストボディー
     */
    public record SignInRequestBody(String account, String password) {
    }
}
