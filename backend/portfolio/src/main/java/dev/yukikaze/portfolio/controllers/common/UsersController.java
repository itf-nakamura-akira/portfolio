package dev.yukikaze.portfolio.controllers.common;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.yukikaze.portfolio.annotations.JwtCookie;
import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.objects.JwtPayload;
import dev.yukikaze.portfolio.services.UsersService;
import dev.yukikaze.portfolio.utils.CookieUtils;

/**
 * ユーザー Controller
 */
@RestController
@RequestMapping("/common/users")
public class UsersController {
    /**
     * CookieUtils
     */
    private final CookieUtils cookieUtils;

    /**
     * UsersService
     */
    private UsersService usersService;

    /**
     * コンストラクター
     *
     * @param cookieUtils  CookieUtils
     * @param usersService UsersService
     */
    public UsersController(CookieUtils cookieUtils, UsersService usersService) {
        this.cookieUtils = cookieUtils;
        this.usersService = usersService;
    }

    /**
     * Cookieを元に認証済みユーザーの情報を取得し、Cookieの有効期限を更新する。
     *
     * @param jwtPayload Cookie情報
     *
     * @return 認証済みユーザーの情報
     */
    @GetMapping("loginUser")
    public LoginUserResponse loginUser(@JwtCookie JwtPayload jwtPayload) {
        UsersEntity user = this.usersService.getUserById(jwtPayload.id());

        return new LoginUserResponse(user.getAccount(), user.getName(), user.getPermission());
    }

    /**
     * 認証済みのユーザーのCookieを削除する
     *
     * @param jwtPayload Cookie情報
     * 
     * @param response   レスポンスデータ
     */
    @PostMapping("logout")
    public void logout(@JwtCookie JwtPayload jwtPayload, HttpServletResponse response) {
        this.cookieUtils.delCookie(response);
    }

    /**
     * loginUser メソッドのレスポンス
     */
    public record LoginUserResponse(String account, String name, UsersPermission permission) {
    }
}
