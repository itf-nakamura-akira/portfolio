package dev.yukikaze.portfolio.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @DisplayName("selectById メソッドのテスト")
    public void selectById() {
        // 「管理者ユーザー」のデータの取得
        UsersEntity adminUser = this.usersMapper.selectById(1L).get();

        // データの確認
        assertEquals(adminUser.getId(), 1L);
        assertEquals(adminUser.getAccount(), "admin");
        assertEquals(adminUser.getPasswordHash(), "$2a$10$NHwSXeZd7ieiIKKGsOqcteNdZoND9VfQqkB9yIdUnNh4Dq48DTv7q");
        assertEquals(adminUser.getName(), "管理者ユーザー");
        assertEquals(adminUser.getPermission(), UsersPermission.Admin);
        assertEquals(adminUser.getIsEnabled(), true);

        // 「齋藤 綾香」のデータの取得
        UsersEntity ichinoheUser = this.usersMapper.selectById(11L).get();

        // データの確認
        assertEquals(ichinoheUser.getId(), 11L);
        assertEquals(ichinoheUser.getAccount(), "ichinohe");
        assertEquals(ichinoheUser.getPasswordHash(), "$2a$10$VcUwVqjSMnJxF.ColcRQEe4E1iUAwlSc8tmlcU2mV/50nt6xF2eUC");
        assertEquals(ichinoheUser.getName(), "一戸 敏雄");
        assertEquals(ichinoheUser.getPermission(), UsersPermission.User);
        assertEquals(ichinoheUser.getIsEnabled(), false);

        // 「参照ユーザー」のデータの取得
        UsersEntity referencerUser = this.usersMapper.selectById(12L).get();

        // データの確認
        assertEquals(referencerUser.getId(), 12L);
        assertEquals(referencerUser.getAccount(), "referencer");
        assertEquals(referencerUser.getPasswordHash(), "$2a$10$Nwig050LweltGBInbomL/ePMZ1Ofspepa.n7dnBJpQTBWDFtl/THO");
        assertEquals(referencerUser.getName(), "参照ユーザー");
        assertEquals(referencerUser.getPermission(), UsersPermission.Referencer);
        assertEquals(referencerUser.getIsEnabled(), true);

        // 存在しないユーザーの取得
        Optional<UsersEntity> notExistsUser = this.usersMapper.selectById(-1L);

        // データの確認
        assertTrue(notExistsUser.isEmpty());
    }

    @Test
    @DisplayName("selectByAccount メソッドのテスト")
    public void selectByAccount() {
        // 「admin」のデータの取得
        UsersEntity adminUser = this.usersMapper.selectByAccount("admin").get();

        // データの確認
        assertEquals(adminUser.getId(), 1L);
        assertEquals(adminUser.getAccount(), "admin");
        assertEquals(adminUser.getPasswordHash(), "$2a$10$NHwSXeZd7ieiIKKGsOqcteNdZoND9VfQqkB9yIdUnNh4Dq48DTv7q");
        assertEquals(adminUser.getName(), "管理者ユーザー");
        assertEquals(adminUser.getPermission(), UsersPermission.Admin);
        assertEquals(adminUser.getIsEnabled(), true);

        // 存在しないユーザーの取得
        Optional<UsersEntity> notExistsUser = this.usersMapper.selectByAccount("not exists user");

        // データの確認
        assertTrue(notExistsUser.isEmpty());
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
        assertNotEquals(addUser.getId(), null);

        // DBから挿入したデータを取得する
        UsersEntity addedUser = this.usersMapper.selectById(addUser.getId()).get();

        // データの確認
        assertEquals(addUser.getId(), addedUser.getId());
        assertEquals(addUser.getAccount(), addedUser.getAccount());
        assertEquals(addUser.getPasswordHash(), addedUser.getPasswordHash());
        assertEquals(addUser.getName(), addedUser.getName());
        assertEquals(addUser.getPermission(), addedUser.getPermission());
        assertEquals(addUser.getIsEnabled(), addedUser.getIsEnabled());

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
        assertTrue(this.usersMapper.updateUser(targetUserId, assertAccount, assertName, assertPermission,
                assertIsEnabled));

        // 更新後のデータ取得
        UsersEntity updateUser = this.usersMapper.selectById(targetUserId).get();

        // データの確認
        assertEquals(updateUser.getAccount(), assertAccount);
        assertEquals(updateUser.getName(), assertName);
        assertEquals(updateUser.getPermission(), assertPermission);
        assertEquals(updateUser.getIsEnabled(), assertIsEnabled);

        // 更新後のデータ(期待値)
        assertAccount = "admin-updated-updated";
        assertName = "管理者ユーザー-updated-updated";
        assertPermission = UsersPermission.Admin;
        assertIsEnabled = true;

        // 更新
        assertTrue(this.usersMapper.updateUser(targetUserId, assertAccount, assertName, assertPermission,
                assertIsEnabled));

        // 更新後のデータ取得
        updateUser = this.usersMapper.selectById(targetUserId).get();

        // データの確認
        assertEquals(updateUser.getAccount(), assertAccount);
        assertEquals(updateUser.getName(), assertName);
        assertEquals(updateUser.getPermission(), assertPermission);
        assertEquals(updateUser.getIsEnabled(), assertIsEnabled);

        // 存在しないIDの更新
        assertFalse(this.usersMapper.updateUser(-1L, "", "", UsersPermission.Admin, true));

        // データの更新(users.accountによる一意性エラー)
        assertThrows(DuplicateKeyException.class, () -> {
            this.usersMapper.updateUser(1L, "kawakami",
                    "管理者ユーザー",
                    UsersPermission.Admin, true);
        });
    }

    @Test
    @DisplayName("updateUserPassword メソッドのテスト")
    public void updateUserPassword() {
        // 更新後のデータ(期待値)
        String assertPassword = "パスワードハッシュ";

        // 更新
        assertTrue(this.usersMapper.updateUserPassword(1L, assertPassword));

        // 更新後のデータ取得
        UsersEntity updateUser = this.usersMapper.selectById(1L).get();

        // データの確認
        assertEquals(updateUser.getPasswordHash(), assertPassword);

        // 存在しないIDの更新
        assertFalse(this.usersMapper.updateUserPassword(-1L, ""));
    }

    @Test
    @DisplayName("deleteUser メソッドのテスト")
    public void deleteUser() {
        // データの削除
        assertTrue(this.usersMapper.deleteUser(1L));

        // 削除後のデータ取得
        assertTrue(this.usersMapper.selectById(1L).isEmpty());

        // 存在しないIDの更新
        assertFalse(this.usersMapper.deleteUser(-1L));
    }
}
