package dev.yukikaze.portfolio.services;

import static org.assertj.core.api.Assertions.assertThat;
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
public class UsersServiceTests {
    /**
     * UsersService
     */
    private UsersService usersService;

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
        assertThat(usersList.size()).isEqualTo(12);

        // データの確認
        assertThat(usersList.get(0).getAccount()).isEqualTo("admin");
        assertThat(usersList.get(1).getAccount()).isEqualTo("saito");
        assertThat(usersList.get(2).getAccount()).isEqualTo("shirahama");
        assertThat(usersList.get(3).getAccount()).isEqualTo("yamaoka");
        assertThat(usersList.get(4).getAccount()).isEqualTo("sakata");
        assertThat(usersList.get(5).getAccount()).isEqualTo("imadu");
        assertThat(usersList.get(6).getAccount()).isEqualTo("kawakami");
        assertThat(usersList.get(7).getAccount()).isEqualTo("shikata");
        assertThat(usersList.get(8).getAccount()).isEqualTo("nojiri");
        assertThat(usersList.get(9).getAccount()).isEqualTo("yahata");
        assertThat(usersList.get(10).getAccount()).isEqualTo("ichinohe");
        assertThat(usersList.get(11).getAccount()).isEqualTo("referencer");
    }

    @Test
    @DisplayName("getUserById メソッドのテスト")
    public void getUserById() {
        // 「管理者ユーザー」の指定
        UsersEntity adminUser = this.usersService.getUserById(1L);

        // データの確認
        assertThat(adminUser.getId()).isEqualTo(1L);
        assertThat(adminUser.getAccount()).isEqualTo("admin");
        assertThat(adminUser.getPasswordHash()).isEqualTo(
                "$2a$10$NHwSXeZd7ieiIKKGsOqcteNdZoND9VfQqkB9yIdUnNh4Dq48DTv7q");
        assertThat(adminUser.getName()).isEqualTo("管理者ユーザー");
        assertThat(adminUser.getPermission()).isEqualTo(UsersPermission.Admin);
        assertThat(adminUser.getIsEnabled()).isEqualTo(true);

        // 存在しないユーザーの指定
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> this.usersService.getUserById(-1L));

        // 例外の確認
        assertThat(e.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(e.getReason()).isEqualTo("存在しないユーザーIDが指定されました。");
    }

    @Test
    @DisplayName("addUser メソッドのテスト")
    public void addUser() {
        // ユーザーデータの追加
        UsersEntity addedUser = this.usersService.addUser("test-user", "パスワードハッシュ",
                "テストユーザー", UsersPermission.Admin, true);

        // 追加したデータの取得
        UsersEntity assertAddedUser = this.usersService.getUserById(addedUser.getId());

        // データの確認
        assertThat(addedUser.getId()).isEqualTo(assertAddedUser.getId());
        assertThat(addedUser.getAccount()).isEqualTo(assertAddedUser.getAccount());
        assertThat(addedUser.getPasswordHash())
                .isEqualTo(assertAddedUser.getPasswordHash());
        assertThat(addedUser.getName()).isEqualTo(assertAddedUser.getName());
        assertThat(addedUser.getPermission()).isEqualTo(assertAddedUser.getPermission());
        assertThat(addedUser.getIsEnabled()).isEqualTo(assertAddedUser.getIsEnabled());

        // 既に登録済みのアカウントの挿入
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> this.usersService.addUser("admin", "パスワードハッシュ", "テストユーザー",
                        UsersPermission.Admin, true));

        // 例外の確認
        assertThat(e.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(e.getReason()).isEqualTo("「admin」というユーザーは既に存在しています。");
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
        assertThat(updatedUser.getAccount()).isEqualTo(assertUpdatedUser.getAccount());
        assertThat(updatedUser.getName()).isEqualTo(assertUpdatedUser.getName());
        assertThat(updatedUser.getPermission())
                .isEqualTo(assertUpdatedUser.getPermission());
        assertThat(updatedUser.getIsEnabled()).isEqualTo(assertUpdatedUser.getIsEnabled());

        // ユーザーデータの更新
        updatedUser = this.usersService.updateUser(1L, "user-update", "テストユーザー-update",
                UsersPermission.User, false);

        // 更新したデータの取得
        assertUpdatedUser = this.usersService.getUserById(1L);

        // データの確認
        assertThat(updatedUser.getAccount()).isEqualTo(assertUpdatedUser.getAccount());
        assertThat(updatedUser.getName()).isEqualTo(assertUpdatedUser.getName());
        assertThat(updatedUser.getPermission())
                .isEqualTo(assertUpdatedUser.getPermission());
        assertThat(updatedUser.getIsEnabled()).isEqualTo(assertUpdatedUser.getIsEnabled());

        // 存在しないユーザーの指定
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> this.usersService.updateUser(-1L, "user-update",
                        "テストユーザー-update", UsersPermission.User, false));

        // 例外の確認
        assertThat(e.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(e.getReason()).isEqualTo("更新対象のデータが見つかりませんでした。");
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
        assertThat(e.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(e.getReason()).isEqualTo("削除対象のデータが見つかりませんでした。");
    }
}
