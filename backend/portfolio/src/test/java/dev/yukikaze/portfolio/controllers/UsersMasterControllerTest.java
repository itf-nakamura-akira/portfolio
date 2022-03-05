package dev.yukikaze.portfolio.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import dev.yukikaze.portfolio.controllers.UsersMasterController.DeleteRequestBody;
import dev.yukikaze.portfolio.controllers.UsersMasterController.ListResponse;
import dev.yukikaze.portfolio.controllers.UsersMasterController.ListUsers;
import dev.yukikaze.portfolio.controllers.UsersMasterController.RegistRequestBody;
import dev.yukikaze.portfolio.controllers.UsersMasterController.UpdateRequestBody;
import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.mappers.UsersMapper;
import dev.yukikaze.portfolio.mappers.UsersMapperMock;
import dev.yukikaze.portfolio.services.UsersService;

/**
 * UsersMasterController のテストコード
 */
@DisplayName("UsersMasterController のテストコード")
public class UsersMasterControllerTest {
    /**
     * UsersMasterController
     */
    private UsersMasterController usersMasterController;

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
        this.usersMasterController = new UsersMasterController(
                new UsersService(this.usersMapper));
    }

    @Test
    @DisplayName("list メソッドのテスト")
    public void list() {
        // ユーザーデータの取得
        ListResponse response = this.usersMasterController.list();
        List<ListUsers> users = response.users();

        // データの確認
        assertEquals(users.size(), 12);
        users.stream().forEach(user -> {
            assertNotNull(user.id());
            assertNotNull(user.account());
            assertNotNull(user.name());
            assertNotNull(user.permission());
            assertNotNull(user.isEnabled());
        });
    }

    @Test
    @DisplayName("regist メソッドのテスト")
    public void regist() throws Exception {
        // ユーザーデータの登録
        ListUsers user = this.usersMasterController.regist(
                new RegistRequestBody("test-account", "テストアカウント", "パスワード",
                        UsersPermission.Admin, true))
                .user();

        // データの確認
        UsersEntity registedUser = this.usersMapper.selectByAccount("test-account").get();
        assertEquals(user.id(), registedUser.getId());
        assertEquals(user.account(), registedUser.getAccount());
        assertEquals(user.name(), registedUser.getName());
        assertEquals(user.permission(), registedUser.getPermission());
        assertEquals(user.isEnabled(), registedUser.getIsEnabled());

        // 登録済みの既存ユーザーデータの登録
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> this.usersMasterController.regist(
                        new RegistRequestBody("test-account", "テストアカウント", "パスワード",
                                UsersPermission.Admin, true)));

        // 例外の確認
        assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(e.getReason(), "「test-account」というユーザーは既に存在しています。");
    }

    @Test
    @DisplayName("update メソッドのテスト")
    public void update() throws Exception {
        // 更新情報
        Long targetId = 1L;
        String account = "update-test";
        String name = "更新テスト";
        UsersPermission permission = UsersPermission.Admin;
        Boolean isEnabled = true;

        // ユーザーデータの更新
        this.usersMasterController
                .update(new UpdateRequestBody(targetId, account, name, permission, isEnabled));

        // データの確認
        UsersEntity updatedUser = this.usersMapper.selectById(targetId).get();
        assertEquals(targetId, updatedUser.getId());
        assertEquals(account, updatedUser.getAccount());
        assertEquals(name, updatedUser.getName());
        assertEquals(permission, updatedUser.getPermission());
        assertEquals(isEnabled, updatedUser.getIsEnabled());

        // 存在しないユーザーデータの更新
        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> this.usersMasterController
                .update(new UpdateRequestBody(-1L, account, name, permission, isEnabled)));

        // 例外の確認
        assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(e.getReason(), "更新対象のデータが見つかりませんでした。");

        // 既に登録済みのアカウントに更新
        e = assertThrows(ResponseStatusException.class, () -> this.usersMasterController
                .update(new UpdateRequestBody(1L, "kawakami", name, permission, isEnabled)));

        // 例外の確認
        assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(e.getReason(), "「kawakami」というユーザーは既に存在しています。");
    }

    @Test
    @DisplayName("delete メソッドのテスト")
    public void delete() {
        // 削除情報
        Long targetId = 1L;

        // ユーザーデータの削除
        this.usersMasterController.delete(new DeleteRequestBody(targetId));

        // データの確認
        assertTrue(this.usersMapper.selectById(targetId).isEmpty());

        // 既に削除済みのアカウントを削除
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> this.usersMasterController.delete(new DeleteRequestBody(targetId)));

        // 例外の確認
        assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(e.getReason(), "対象のデータは既に削除されています。");
    }
}
