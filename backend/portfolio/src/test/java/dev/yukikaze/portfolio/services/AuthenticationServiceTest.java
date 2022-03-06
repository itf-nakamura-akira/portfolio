package dev.yukikaze.portfolio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.mappers.UsersMapperMock;

/**
 * AuthenticationService のテストコード
 */
@DisplayName("AuthenticationService のテストコード")
public class AuthenticationServiceTest {
    /**
     * AuthenticationService
     */
    private AuthenticationService authenticationService;

    /**
     * 初期化
     */
    @BeforeEach
    public void beforeEach() {
        this.authenticationService = new AuthenticationService(new UsersMapperMock());
    }

    @Test
    @DisplayName("verify メソッドのテスト")
    public void verify() {
        Optional<UsersEntity> result;

        // 正しい認証情報のテスト(Admin)
        result = this.authenticationService.verify("admin", "admin");

        assertTrue(result.isPresent());
        assertEquals(result.get().getId(), 1L);
        assertEquals(result.get().getPermission(), UsersPermission.Admin);

        // 正しい認証情報のテスト(User)
        result = this.authenticationService.verify("kawakami", "kawakami");

        assertTrue(result.isPresent());
        assertEquals(result.get().getId(), 7L);
        assertEquals(result.get().getPermission(), UsersPermission.User);

        // 正しい認証情報のテスト(referencer)
        result = this.authenticationService.verify("referencer", "referencer");

        assertTrue(result.isPresent());
        assertEquals(result.get().getId(), 12L);
        assertEquals(result.get().getPermission(), UsersPermission.Referencer);

        // 間違った認証情報のテスト(ユーザーは有効)
        result = this.authenticationService.verify("referencer", "invalid");

        assertTrue(result.isEmpty());

        // 無効なユーザーのテスト(パスワードは正しい)
        result = this.authenticationService.verify("ichinohe", "ichinohe");

        assertTrue(result.isEmpty());
    }
}
