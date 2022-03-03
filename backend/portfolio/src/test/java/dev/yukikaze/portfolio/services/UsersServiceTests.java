package dev.yukikaze.portfolio.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.mappers.UsersMapperMock;

/**
 * UsersService のテストコード
 */
public class UsersServiceTests {
    /**
     * UsersService
     */
    private static final UsersService usersService = new UsersService(new UsersMapperMock());

    /**
     * findAll メソッドのテスト
     */
    @Test
    public void findAll() {
        // ユーザーデータの取得
        List<UsersEntity> usersList = usersService.findAll();

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
        // 存在しないユーザーの指定
        ResponseStatusException e =
                assertThrows(ResponseStatusException.class, () -> usersService.findById(-1L));

        // 例外の確認
        assertThat(e.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(e.getReason()).isEqualTo("存在しないユーザーIDが指定されました。");
    }

    /**
     * insertUser メソッドのテスト
     */
    @Test
    public void insertUser() {
        // 追加するデータ
        String assertAccount = "test-user";
        String assertPassword = "パスワードハッシュ";
        String assertName = "テストユーザー";
        UsersPermission assertPermission = UsersPermission.Admin;
        Boolean assertIsEnabled = true;

        // ユーザーデータの取得
        UsersEntity addedUser = usersService.insertUser(assertAccount, assertPassword, assertName,
                assertPermission, assertIsEnabled);

        // データの確認
        assertThat(addedUser.getId()).isNotEqualTo(null);
        assertThat(addedUser.getAccount()).isEqualTo(assertAccount);
        assertThat(addedUser.getPasswordHash()).isNotEqualTo(assertPassword);
        assertThat(addedUser.getName()).isEqualTo(assertName);
        assertThat(addedUser.getPermission()).isEqualTo(assertPermission);
        assertThat(addedUser.getIsEnabled()).isEqualTo(assertIsEnabled);

        // TODO: アカウントが被ってた場合の例外テストの追加
    }
}
