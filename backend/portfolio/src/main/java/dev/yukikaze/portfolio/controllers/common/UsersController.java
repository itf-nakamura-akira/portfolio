package dev.yukikaze.portfolio.controllers.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.yukikaze.portfolio.annotations.JwtCookie;
import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.objects.JwtPayload;
import dev.yukikaze.portfolio.services.UsersService;

/**
 * ユーザー Controller
 */
@RestController
@RequestMapping("/common/users")
public class UsersController {
    /**
     * UsersService
     */
    private UsersService usersService;

    /**
     * コンストラクター
     *
     * @param usersService UsersService
     */
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Cookieを元に認証済みユーザーの情報を取得し、Cookieの有効期限を更新する。
     *
     * @return 認証済みユーザーの情報
     */
    @GetMapping("signedInUser")
    public SignedInUserResponse signedInUser(@JwtCookie JwtPayload jwtPayload) {
        UsersEntity user = this.usersService.getUserById(jwtPayload.id());

        return new SignedInUserResponse(user.getAccount(), user.getName(), user.getPermission());
    }

    /**
     * signedInUserメソッドのレスポンス
     */
    public record SignedInUserResponse(String account, String name, UsersPermission permission) {
    }
}
