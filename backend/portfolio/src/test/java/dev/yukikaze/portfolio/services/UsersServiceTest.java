package dev.yukikaze.portfolio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.mappers.UsersMapperMock;

/**
 * UsersService のテストコード
 */
@DisplayName("UsersService のテストコード")
public class UsersServiceTest {
    /**
     * UsersService
     */
    private UsersService usersService;

    /**
     * 初期化
     */
    @BeforeEach
    public void beforeEach() {
        this.usersService = new UsersService(new UsersMapperMock());
    }

    @Test
    @DisplayName("getUsersAll メソッドのテスト")
    public void getUsersAll() {
        // ユーザーデータの取得
        List<UsersEntity> usersList = this.usersService.getUsersAll();

        // 件数の確認
        assertEquals(usersList.size(), 12);

        // データの確認
        assertEquals(usersList.get(0).getAccount(), "admin");
        assertEquals(usersList.get(1).getAccount(), "saito");
        assertEquals(usersList.get(2).getAccount(), "shirahama");
        assertEquals(usersList.get(3).getAccount(), "yamaoka");
        assertEquals(usersList.get(4).getAccount(), "sakata");
        assertEquals(usersList.get(5).getAccount(), "imadu");
        assertEquals(usersList.get(6).getAccount(), "kawakami");
        assertEquals(usersList.get(7).getAccount(), "shikata");
        assertEquals(usersList.get(8).getAccount(), "nojiri");
        assertEquals(usersList.get(9).getAccount(), "yahata");
        assertEquals(usersList.get(10).getAccount(), "ichinohe");
        assertEquals(usersList.get(11).getAccount(), "referencer");
    }

    @Test
    @DisplayName("getUserById メソッドのテスト")
    public void getUserById() {
        // 「管理者ユーザー」の指定
        UsersEntity adminUser = this.usersService.getUserById(1L);

        // データの確認
        assertEquals(adminUser.getId(), 1L);
        assertEquals(adminUser.getAccount(), "admin");
        assertEquals(adminUser.getPasswordHash(), "$2a$10$NHwSXeZd7ieiIKKGsOqcteNdZoND9VfQqkB9yIdUnNh4Dq48DTv7q");
        assertEquals(adminUser.getName(), "管理者ユーザー");
        assertEquals(adminUser.getPermission(), UsersPermission.Admin);
        assertEquals(adminUser.getIsEnabled(), true);

        // 存在しないユーザーの指定
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> this.usersService.getUserById(-1L));

        // 例外の確認
        assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(e.getReason(), "ユーザー情報の取得に失敗しました。");
    }

    @Test
    @DisplayName("registUser メソッドのテスト")
    public void registUser() {
        // ユーザーデータの追加
        UsersEntity addedUser = this.usersService.registUser("test-user", "パスワードハッシュ",
                "テストユーザー", UsersPermission.Admin, true);

        // 追加したデータの取得
        UsersEntity assertAddedUser = this.usersService.getUserById(addedUser.getId());

        // データの確認
        assertEquals(addedUser.getId(), assertAddedUser.getId());
        assertEquals(addedUser.getAccount(), assertAddedUser.getAccount());
        assertEquals(addedUser.getPasswordHash(), assertAddedUser.getPasswordHash());
        assertEquals(addedUser.getName(), assertAddedUser.getName());
        assertEquals(addedUser.getPermission(), assertAddedUser.getPermission());
        assertEquals(addedUser.getIsEnabled(), assertAddedUser.getIsEnabled());

        // 既に登録済みのアカウントの挿入
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> this.usersService.registUser("admin", "パスワードハッシュ", "テストユーザー",
                        UsersPermission.Admin, true));

        // 例外の確認
        assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(e.getReason(), "「admin」というユーザーは既に存在しています。");
    }

    @Test
    @DisplayName("updateUser メソッドのテスト")
    public void updateUser() {
        // ユーザーデータの更新
        UsersEntity updatedUser = this.usersService.updateUser(1L, "user-update",
                "テストユーザー-update", UsersPermission.Admin, true);

        // 更新したデータの取得
        UsersEntity assertUpdatedUser = this.usersService.getUserById(1L);

        // データの確認
        assertEquals(updatedUser.getAccount(), assertUpdatedUser.getAccount());
        assertEquals(updatedUser.getName(), assertUpdatedUser.getName());
        assertEquals(updatedUser.getPermission(), assertUpdatedUser.getPermission());
        assertEquals(updatedUser.getIsEnabled(), assertUpdatedUser.getIsEnabled());

        // ユーザーデータの更新
        updatedUser = this.usersService.updateUser(12L, "user-update2", "テストユーザー-update",
                UsersPermission.User, false);

        // 更新したデータの取得
        assertUpdatedUser = this.usersService.getUserById(12L);

        // データの確認
        assertEquals(updatedUser.getAccount(), assertUpdatedUser.getAccount());
        assertEquals(updatedUser.getName(), assertUpdatedUser.getName());
        assertEquals(updatedUser.getPermission(), assertUpdatedUser.getPermission());
        assertEquals(updatedUser.getIsEnabled(), assertUpdatedUser.getIsEnabled());

        // 存在しないユーザーの指定
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> this.usersService.updateUser(-1L, "user-update",
                        "テストユーザー-update", UsersPermission.User, false));

        // 例外の確認
        assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(e.getReason(), "更新対象のデータが見つかりませんでした。");

        // 既に登録済みのアカウントに更新
        e = assertThrows(ResponseStatusException.class,
                () -> this.usersService.updateUser(1L, "kawakami", "管理者ユーザー", UsersPermission.Admin, true));

        // 例外の確認
        assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(e.getReason(), "「kawakami」というユーザーは既に存在しています。");

        // 管理者ユーザーが0人になるような更新(1)
        e = assertThrows(ResponseStatusException.class,
                () -> this.usersService.updateUser(1L, "user-update",
                        "テストユーザー-update", UsersPermission.User, true));

        // 例外の確認
        assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(e.getReason(), "有効な管理者ユーザーが0人にならないようにしてください。");

        // 管理者ユーザーが0人になるような更新(2)
        e = assertThrows(ResponseStatusException.class,
                () -> this.usersService.updateUser(1L, "user-update",
                        "テストユーザー-update", UsersPermission.Admin, false));

        // 例外の確認
        assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(e.getReason(), "有効な管理者ユーザーが0人にならないようにしてください。");
    }

    @Test
    @DisplayName("updatePassword メソッドのテスト")
    public void updatePassword() {
        // 更新前のデータの取得
        String beforeUpdatePassword = this.usersService.getUserById(1L).getPasswordHash();

        // パスワードの更新
        this.usersService.updatePassword(1L, "password");

        // 更新後のデータの取得
        String afterUpdatePassword = this.usersService.getUserById(1L).getPasswordHash();

        // データの確認
        assertNotEquals("password", afterUpdatePassword);
        assertNotEquals(beforeUpdatePassword, afterUpdatePassword);

        // 存在しないユーザーの指定
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> this.usersService.updatePassword(-1L, "password"));

        // 例外の確認
        assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(e.getReason(), "更新対象のデータが見つかりませんでした。");
    }

    @Test
    @DisplayName("deleteUser メソッドのテスト")
    public void deleteUser() {
        // ユーザーデータの削除
        this.usersService.deleteUser(1L);

        // 削除したユーザーの指定
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> this.usersService.getUserById(1L));

        // 存在しないユーザーの指定
        e = assertThrows(ResponseStatusException.class,
                () -> this.usersService.deleteUser(-1L));

        // 例外の確認
        assertEquals(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(e.getReason(), "対象のデータは既に削除されています。");
    }
}
