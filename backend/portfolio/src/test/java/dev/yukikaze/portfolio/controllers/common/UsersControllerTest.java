package dev.yukikaze.portfolio.controllers.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.yukikaze.portfolio.controllers.common.UsersController.SignedInUserResponse;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.mappers.UsersMapper;
import dev.yukikaze.portfolio.mappers.UsersMapperMock;
import dev.yukikaze.portfolio.objects.JwtPayload;
import dev.yukikaze.portfolio.services.UsersService;
import dev.yukikaze.portfolio.utils.JwtUtils;
import dev.yukikaze.portfolio.utils.JwtUtilsTest;

/**
 * UsersController のテストコード
 */
@DisplayName("UsersController のテストコード")
public class UsersControllerTest {
    /**
     * UsersController
     */
    private UsersController usersController;

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
        this.usersController = new UsersController(new UsersService(this.usersMapper));
    }

    @Test
    @DisplayName("signedInUser メソッドのテスト")
    public void signedInUser() {
        JwtUtils jwtUtils = JwtUtilsTest.generateJwtUtils("7", "secret");
        String token;
        JwtPayload jwtPayload;
        SignedInUserResponse response;

        // 管理者ユーザーのアクセス
        token = jwtUtils.generateToken(1L, UsersPermission.Admin);
        jwtPayload = jwtUtils.verifyToken(token).get();
        response = this.usersController.signedInUser(jwtPayload);

        assertEquals("admin", response.account());
        assertEquals("管理者ユーザー", response.name());
        assertEquals(UsersPermission.Admin, response.permission());

        // 一般ユーザーのアクセス
        token = jwtUtils.generateToken(2L, UsersPermission.User);
        jwtPayload = jwtUtils.verifyToken(token).get();
        response = this.usersController.signedInUser(jwtPayload);

        assertEquals("saito", response.account());
        assertEquals("齋藤 綾香", response.name());
        assertEquals(UsersPermission.User, response.permission());

        // 参照ユーザーのアクセス
        token = jwtUtils.generateToken(12L, UsersPermission.Referencer);
        jwtPayload = jwtUtils.verifyToken(token).get();
        response = this.usersController.signedInUser(jwtPayload);

        assertEquals("referencer", response.account());
        assertEquals("参照ユーザー", response.name());
        assertEquals(UsersPermission.Referencer, response.permission());
    }
}
