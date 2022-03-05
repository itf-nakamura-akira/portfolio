package dev.yukikaze.portfolio.mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;

import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.enums.UsersPermission;

/**
 * UsersMapper のモック
 */
public class UsersMapperMock implements UsersMapper {
    /**
     * モックデータ
     */
    private final List<UsersEntity> mockData = new ArrayList<UsersEntity>(Arrays.asList(
            new UsersEntity(1L, "admin", "$2a$10$NHwSXeZd7ieiIKKGsOqcteNdZoND9VfQqkB9yIdUnNh4Dq48DTv7q",
                    "管理者ユーザー", UsersPermission.Admin, true),
            new UsersEntity(2L, "saito", "$2a$10$ZDcm9KoNyZske8wAFnsyu.VbaeoaNgWwuFmff3mMe2Yrf39aT0IJu",
                    "齋藤 綾香", UsersPermission.User, true),
            new UsersEntity(3L, "shirahama", "$2a$10$6wSX9vpPzUorr.TSGaO4CObyvt6aZHicTEULdk.gJCtSK6SdfbJCC",
                    "白浜 隆二", UsersPermission.User, true),
            new UsersEntity(4L, "yamaoka", "$2a$10$91BFfXg80xm1KvVeSzmcMeTjq8kdIJWo0sF9wFGbCP6S1v5oL5dMK",
                    "山岡 友治", UsersPermission.User, true),
            new UsersEntity(5L, "sakata", "$2a$10$bUlnL/CJw/1nkS.o7mzANO0Zx7d5DvxWa83fSGHOJVSRNfqac1G72",
                    "阪田 智恵", UsersPermission.User, true),
            new UsersEntity(6L, "imadu", "$2a$10$QBmA5RGycbyQZOUUzzf9POEkWatY0qoQxfubZnag0cPVzSDCn9Ebq",
                    "今津 重光", UsersPermission.User, true),
            new UsersEntity(7L, "kawakami", "$2a$10$XwxrNn0B2dUB30F/BM61EuhGCWSnHKch8gP7XgQNR5P/1Rx1WlwpK",
                    "川上 伸夫", UsersPermission.User, true),
            new UsersEntity(8L, "shikata", "$2a$10$qFveWYis.1ZGtzeDhx5Id.tjJxkY2pIm1GVMwKZ0nvnTqz/ZKLrou",
                    "四方 謙三", UsersPermission.User, true),
            new UsersEntity(9L, "nojiri", "$2a$10$5YRzeddgE6s7cZUqcPNBw.Nqqy24/pFCOC0Bpu6hbakGuASJtyyMO",
                    "野尻 欧子", UsersPermission.User, true),
            new UsersEntity(10L, "yahata", "$2a$10$ZlLj/QQ9ta0mnIFg3gFe4O8fZ3j2TgviSB/YW2IJKyBk24sfMHMZ6",
                    "八幡 雅也", UsersPermission.User, true),
            new UsersEntity(11L, "ichinohe", "$2a$10$VcUwVqjSMnJxF.ColcRQEe4E1iUAwlSc8tmlcU2mV/50nt6xF2eUC",
                    "一戸 敏雄", UsersPermission.User, false),
            new UsersEntity(12L, "referencer",
                    "$2a$10$Nwig050LweltGBInbomL/ePMZ1Ofspepa.n7dnBJpQTBWDFtl/THO",
                    "参照ユーザー",
                    UsersPermission.Referencer, true)));

    /**
     * 全件取得
     *
     * @return テーブルの全データ
     */
    public List<UsersEntity> selectAll() {
        return this.mockData;
    }

    /**
     * IDを指定してユーザーを取得する
     *
     * @param id ID
     *
     * @return 指定した1件のデータ
     */
    public Optional<UsersEntity> selectById(Long id) {
        return this.mockData.stream().filter(row -> row.getId().equals(id)).findFirst();
    }

    /**
     * アカウントを指定してユーザーを取得する
     *
     * @param account アカウント
     *
     * @return 指定した1件のデータ
     */
    public Optional<UsersEntity> selectByAccount(String account) {
        return this.mockData.stream().filter(row -> row.getAccount().equals(account))
                .findFirst();
    }

    /**
     * ユーザーの追加
     *
     * @param user 挿入するユーザーデータ
     */
    public void insertUser(UsersEntity user) {
        // accountが重複しているかチェック
        if (this.mockData.stream()
                .anyMatch(row -> row.getAccount().equals(user.getAccount()))) {
            throw new DuplicateKeyException("");
        }

        // 擬似的にID割り当てる
        user.setId(this.mockData.stream()
                .max((row1, row2) -> Long.compare(row1.getId(), row2.getId())).get()
                .getId() + 1);

        // 追加
        this.mockData.add(user);
    }

    /**
     * ユーザーの更新
     *
     * @param id         ID
     * @param account    アカウント
     * @param name       表示名
     * @param permission ユーザー権限
     * @param isEnabled  有効フラグ
     * 
     * @return 1件以上更新されたらtrue 0件だとfalse
     */
    public boolean updateUser(Long id, String account, String name, UsersPermission permission,
            Boolean isEnabled) {
        // 更新対象の取得
        Optional<UsersEntity> target = this.mockData.stream()
                .filter(row -> row.getId().equals(id)).findFirst();

        if (target.isEmpty()) {
            return false;
        }

        // users.accountが被っていないか
        if (this.mockData.stream().anyMatch(row -> !row.getId().equals(id) && row.getAccount().equals(account))) {
            throw new DuplicateKeyException("");
        }

        target.get().setAccount(account);
        target.get().setName(name);
        target.get().setPermission(permission);
        target.get().setIsEnabled(isEnabled);

        return true;
    }

    /**
     * パスワードの更新
     *
     * @param id           ID
     * @param passwordHash パスワードハッシュ
     * 
     * @return 1件以上更新されたらtrue 0件だとfalse
     */
    public boolean updateUserPassword(Long id, String passwordHash) {
        Optional<UsersEntity> target = this.mockData.stream()
                .filter(row -> row.getId().equals(id)).findFirst();

        if (target.isEmpty()) {
            return false;
        }

        target.get().setPasswordHash(passwordHash);

        return true;
    }

    /**
     * ユーザーの削除
     *
     * @param id ID
     * 
     * @return 1件以上削除されたらtrue 0件だとfalse
     */
    public boolean deleteUser(Long id) {
        return this.mockData.removeIf(v -> v.getId().equals(id));
    }
}
