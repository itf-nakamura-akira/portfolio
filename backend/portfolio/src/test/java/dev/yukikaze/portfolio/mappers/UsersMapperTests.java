package dev.yukikaze.portfolio.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import java.util.Optional;
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
public class UsersMapperTests {
    /**
     * UsersMapper
     */
    @Autowired
    private UsersMapper usersMapper;

    /**
     * findAll メソッドのテスト
     */
    @Test
    public void findAll() {
        // データの取得
        List<UsersEntity> usersList = this.usersMapper.findAll();

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

    /**
     * findById メソッドのテスト
     */
    @Test
    public void findById() {
        // 「管理者ユーザー」のデータの取得
        UsersEntity adminUser = this.usersMapper.findById(1L).get();

        // データの確認
        assertThat(adminUser.getId()).isEqualTo(1L);
        assertThat(adminUser.getAccount()).isEqualTo("admin");
        assertThat(adminUser.getPasswordHash())
                .isEqualTo("$2a$10$NHwSXeZd7ieiIKKGsOqcteNdZoND9VfQqkB9yIdUnNh4Dq48DTv7q");
        assertThat(adminUser.getName()).isEqualTo("管理者ユーザー");
        assertThat(adminUser.getPermission()).isEqualTo(UsersPermission.Admin);
        assertThat(adminUser.getIsEnabled()).isEqualTo(true);

        // 「齋藤 綾香」のデータの取得
        UsersEntity ichinoheUser = this.usersMapper.findById(11L).get();

        // データの確認
        assertThat(ichinoheUser.getId()).isEqualTo(11L);
        assertThat(ichinoheUser.getAccount()).isEqualTo("ichinohe");
        assertThat(ichinoheUser.getPasswordHash())
                .isEqualTo("$2a$10$VcUwVqjSMnJxF.ColcRQEe4E1iUAwlSc8tmlcU2mV/50nt6xF2eUC");
        assertThat(ichinoheUser.getName()).isEqualTo("一戸 敏雄");
        assertThat(ichinoheUser.getPermission()).isEqualTo(UsersPermission.User);
        assertThat(ichinoheUser.getIsEnabled()).isEqualTo(false);

        // 「参照ユーザー」のデータの取得
        UsersEntity referencerUser = this.usersMapper.findById(12L).get();

        // データの確認
        assertThat(referencerUser.getId()).isEqualTo(12L);
        assertThat(referencerUser.getAccount()).isEqualTo("referencer");
        assertThat(referencerUser.getPasswordHash())
                .isEqualTo("$2a$10$Nwig050LweltGBInbomL/ePMZ1Ofspepa.n7dnBJpQTBWDFtl/THO");
        assertThat(referencerUser.getName()).isEqualTo("参照ユーザー");
        assertThat(referencerUser.getPermission()).isEqualTo(UsersPermission.Referencer);
        assertThat(referencerUser.getIsEnabled()).isEqualTo(true);

        // 存在しないユーザーの取得
        Optional<UsersEntity> notExistsUser = this.usersMapper.findById(-1L);

        // データの確認
        assertThat(notExistsUser).isEmpty();
    }

    /**
     * findByAccount メソッドのテスト
     */
    @Test
    public void findByAccount() {
        // 「admin」のデータの取得
        UsersEntity adminUser = this.usersMapper.findByAccount("admin").get();

        // データの確認
        assertThat(adminUser.getId()).isEqualTo(1L);
        assertThat(adminUser.getAccount()).isEqualTo("admin");
        assertThat(adminUser.getPasswordHash())
                .isEqualTo("$2a$10$NHwSXeZd7ieiIKKGsOqcteNdZoND9VfQqkB9yIdUnNh4Dq48DTv7q");
        assertThat(adminUser.getName()).isEqualTo("管理者ユーザー");
        assertThat(adminUser.getPermission()).isEqualTo(UsersPermission.Admin);
        assertThat(adminUser.getIsEnabled()).isEqualTo(true);

        // 存在しないユーザーの取得
        Optional<UsersEntity> notExistsUser = this.usersMapper.findByAccount("not exists user");

        // データの確認
        assertThat(notExistsUser).isEmpty();
    }

    /**
     * insertUser メソッドのテスト
     */
    @Test
    public void insertUser() {
        // INSERTするusersデータ
        var addUser = new UsersEntity();
        addUser.setAccount("test-user");
        addUser.setPasswordHash("パスワードハッシュ");
        addUser.setName("テストユーザー");
        addUser.setPermission(UsersPermission.Admin);
        addUser.setIsEnabled(true);

        // データの挿入
        Integer addedCount = this.usersMapper.insertUser(addUser);

        // 挿入された件数が1件
        assertThat(addedCount).isEqualTo(1);

        // IDが自動的に割り振られている
        assertThat(addUser.getId()).isNotEqualTo(null);

        // DBから挿入したデータを取得する
        UsersEntity addedUser = this.usersMapper.findById(addUser.getId()).get();

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
}
