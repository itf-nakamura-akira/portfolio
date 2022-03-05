package dev.yukikaze.portfolio.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.enums.UsersPermission;

/**
 * UsersMapper のテストコード
 */
@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("UsersMapper のテストコード")
public class UsersMapperTests {
    /**
     * UsersMapper
     */
    @Autowired
    private UsersMapper usersMapper;

    @Test
    @DisplayName("selectAll メソッドのテスト")
    public void selectAll() {
        // データの取得
        List<UsersEntity> usersList = this.usersMapper.selectAll();

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
    @DisplayName("selectById メソッドのテスト")
    public void selectById() {
        // 「管理者ユーザー」のデータの取得
        UsersEntity adminUser = this.usersMapper.selectById(1L).get();

        // データの確認
        assertThat(adminUser.getId()).isEqualTo(1L);
        assertThat(adminUser.getAccount()).isEqualTo("admin");
        assertThat(adminUser.getPasswordHash())
                .isEqualTo("$2a$10$NHwSXeZd7ieiIKKGsOqcteNdZoND9VfQqkB9yIdUnNh4Dq48DTv7q");
        assertThat(adminUser.getName()).isEqualTo("管理者ユーザー");
        assertThat(adminUser.getPermission()).isEqualTo(UsersPermission.Admin);
        assertThat(adminUser.getIsEnabled()).isEqualTo(true);

        // 「齋藤 綾香」のデータの取得
        UsersEntity ichinoheUser = this.usersMapper.selectById(11L).get();

        // データの確認
        assertThat(ichinoheUser.getId()).isEqualTo(11L);
        assertThat(ichinoheUser.getAccount()).isEqualTo("ichinohe");
        assertThat(ichinoheUser.getPasswordHash())
                .isEqualTo("$2a$10$VcUwVqjSMnJxF.ColcRQEe4E1iUAwlSc8tmlcU2mV/50nt6xF2eUC");
        assertThat(ichinoheUser.getName()).isEqualTo("一戸 敏雄");
        assertThat(ichinoheUser.getPermission()).isEqualTo(UsersPermission.User);
        assertThat(ichinoheUser.getIsEnabled()).isEqualTo(false);

        // 「参照ユーザー」のデータの取得
        UsersEntity referencerUser = this.usersMapper.selectById(12L).get();

        // データの確認
        assertThat(referencerUser.getId()).isEqualTo(12L);
        assertThat(referencerUser.getAccount()).isEqualTo("referencer");
        assertThat(referencerUser.getPasswordHash())
                .isEqualTo("$2a$10$Nwig050LweltGBInbomL/ePMZ1Ofspepa.n7dnBJpQTBWDFtl/THO");
        assertThat(referencerUser.getName()).isEqualTo("参照ユーザー");
        assertThat(referencerUser.getPermission()).isEqualTo(UsersPermission.Referencer);
        assertThat(referencerUser.getIsEnabled()).isEqualTo(true);

        // 存在しないユーザーの取得
        Optional<UsersEntity> notExistsUser = this.usersMapper.selectById(-1L);

        // データの確認
        assertThat(notExistsUser).isEmpty();
    }

    @Test
    @DisplayName("selectByAccount メソッドのテスト")
    public void selectByAccount() {
        // 「admin」のデータの取得
        UsersEntity adminUser = this.usersMapper.selectByAccount("admin").get();

        // データの確認
        assertThat(adminUser.getId()).isEqualTo(1L);
        assertThat(adminUser.getAccount()).isEqualTo("admin");
        assertThat(adminUser.getPasswordHash())
                .isEqualTo("$2a$10$NHwSXeZd7ieiIKKGsOqcteNdZoND9VfQqkB9yIdUnNh4Dq48DTv7q");
        assertThat(adminUser.getName()).isEqualTo("管理者ユーザー");
        assertThat(adminUser.getPermission()).isEqualTo(UsersPermission.Admin);
        assertThat(adminUser.getIsEnabled()).isEqualTo(true);

        // 存在しないユーザーの取得
        Optional<UsersEntity> notExistsUser = this.usersMapper.selectByAccount("not exists user");

        // データの確認
        assertThat(notExistsUser).isEmpty();
    }

    @Test
    @DisplayName("insertUser メソッドのテスト")
    public void insertUser() {
        // INSERTするusersデータ
        var addUser = new UsersEntity();
        addUser.setAccount("test-user");
        addUser.setPasswordHash("パスワードハッシュ");
        addUser.setName("テストユーザー");
        addUser.setPermission(UsersPermission.Admin);
        addUser.setIsEnabled(true);

        // データの挿入
        this.usersMapper.insertUser(addUser);

        // IDが自動的に割り振られている
        assertThat(addUser.getId()).isNotEqualTo(null);

        // DBから挿入したデータを取得する
        UsersEntity addedUser = this.usersMapper.selectById(addUser.getId()).get();

        // データの確認
        assertThat(addUser.getId()).isEqualTo(addedUser.getId());
        assertThat(addUser.getAccount()).isEqualTo(addedUser.getAccount());
        assertThat(addUser.getPasswordHash()).isEqualTo(addedUser.getPasswordHash());
        assertThat(addUser.getName()).isEqualTo(addedUser.getName());
        assertThat(addUser.getPermission()).isEqualTo(addedUser.getPermission());
        assertThat(addUser.getIsEnabled()).isEqualTo(addedUser.getIsEnabled());

        // 既に存在しているアカウントでINSERTする
        var existsUser = new UsersEntity();
        existsUser.setAccount("admin");
        existsUser.setPasswordHash("パスワードハッシュ");
        existsUser.setName("既存ユーザー");
        existsUser.setPermission(UsersPermission.Admin);
        existsUser.setIsEnabled(true);

        // データの挿入(users.accountによる一意性エラー)
        assertThrows(DuplicateKeyException.class, () -> this.usersMapper.insertUser(existsUser));
    }

    @Test
    @DisplayName("updateUser メソッドのテスト")
    public void updateUser() {
        // 更新対象
        Long targetUserId = 1L;

        // 更新後のデータ(期待値)
        String assertAccount = "admin-updated";
        String assertName = "管理者ユーザー-updated";
        UsersPermission assertPermission = UsersPermission.User;
        Boolean assertIsEnabled = false;

        // 更新
        assertThat(this.usersMapper.updateUser(targetUserId, assertAccount, assertName,
                assertPermission, assertIsEnabled)).isTrue();

        // 更新後のデータ取得
        UsersEntity updateUser = this.usersMapper.selectById(targetUserId).get();

        // データの確認
        assertThat(updateUser.getAccount()).isEqualTo(assertAccount);
        assertThat(updateUser.getName()).isEqualTo(assertName);
        assertThat(updateUser.getPermission()).isEqualTo(assertPermission);
        assertThat(updateUser.getIsEnabled()).isEqualTo(assertIsEnabled);

        // 更新後のデータ(期待値)
        assertAccount = "admin-updated-updated";
        assertName = "管理者ユーザー-updated-updated";
        assertPermission = UsersPermission.Admin;
        assertIsEnabled = true;

        // 更新
        assertThat(this.usersMapper.updateUser(targetUserId, assertAccount, assertName,
                assertPermission, assertIsEnabled)).isTrue();

        // 更新後のデータ取得
        updateUser = this.usersMapper.selectById(targetUserId).get();

        // データの確認
        assertThat(updateUser.getAccount()).isEqualTo(assertAccount);
        assertThat(updateUser.getName()).isEqualTo(assertName);
        assertThat(updateUser.getPermission()).isEqualTo(assertPermission);
        assertThat(updateUser.getIsEnabled()).isEqualTo(assertIsEnabled);

        // 存在しないIDの更新
        assertThat(this.usersMapper.updateUser(-1L, "", "", UsersPermission.Admin, true)).isFalse();
    }

    @Test
    @DisplayName("updateUserPassword メソッドのテスト")
    public void updateUserPassword() {
        // 更新後のデータ(期待値)
        String assertPassword = "パスワードハッシュ";

        // 更新
        assertThat(this.usersMapper.updateUserPassword(1L, assertPassword)).isTrue();

        // 更新後のデータ取得
        UsersEntity updateUser = this.usersMapper.selectById(1L).get();

        // データの確認
        assertThat(updateUser.getPasswordHash()).isEqualTo(assertPassword);

        // 存在しないIDの更新
        assertThat(this.usersMapper.updateUserPassword(-1L, "")).isFalse();
    }

    @Test
    @DisplayName("deleteUser メソッドのテスト")
    public void deleteUser() {
        // データの削除
        assertThat(this.usersMapper.deleteUser(1L)).isTrue();

        // 削除後のデータ取得
        assertThat(this.usersMapper.selectById(1L)).isEmpty();

        // 存在しないIDの更新
        assertThat(this.usersMapper.deleteUser(-1L)).isFalse();
    }
}
