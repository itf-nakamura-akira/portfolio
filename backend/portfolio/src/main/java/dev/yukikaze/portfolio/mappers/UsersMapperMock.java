package dev.yukikaze.portfolio.mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.enums.UsersPermission;

/**
 * UsersMapper のモック
 */
public class UsersMapperMock implements UsersMapper {
    /**
     * モックデータ
     */
    private final List<UsersEntity> MOCK_DATA = new ArrayList<UsersEntity>(Arrays.asList(
            new UsersEntity(1L, "admin",
                    "$2a$10$NHwSXeZd7ieiIKKGsOqcteNdZoND9VfQqkB9yIdUnNh4Dq48DTv7q", "管理者ユーザー",
                    UsersPermission.Admin, true),
            new UsersEntity(2L, "saito",
                    "$2a$10$ZDcm9KoNyZske8wAFnsyu.VbaeoaNgWwuFmff3mMe2Yrf39aT0IJu", "齋藤 綾香",
                    UsersPermission.User, true),
            new UsersEntity(13L, "shirahama",
                    "$2a$10$6wSX9vpPzUorr.TSGaO4CObyvt6aZHicTEULdk.gJCtSK6SdfbJCC", "白浜 隆二",
                    UsersPermission.User, true),
            new UsersEntity(14L, "yamaoka",
                    "$2a$10$91BFfXg80xm1KvVeSzmcMeTjq8kdIJWo0sF9wFGbCP6S1v5oL5dMK", "山岡 友治",
                    UsersPermission.User, true),
            new UsersEntity(15L, "sakata",
                    "$2a$10$bUlnL/CJw/1nkS.o7mzANO0Zx7d5DvxWa83fSGHOJVSRNfqac1G72", "阪田 智恵",
                    UsersPermission.User, true),
            new UsersEntity(16L, "imadu",
                    "$2a$10$QBmA5RGycbyQZOUUzzf9POEkWatY0qoQxfubZnag0cPVzSDCn9Ebq", "今津 重光",
                    UsersPermission.User, true),
            new UsersEntity(17L, "kawakami",
                    "$2a$10$XwxrNn0B2dUB30F/BM61EuhGCWSnHKch8gP7XgQNR5P/1Rx1WlwpK", "川上 伸夫",
                    UsersPermission.User, true),
            new UsersEntity(18L, "shikata",
                    "$2a$10$qFveWYis.1ZGtzeDhx5Id.tjJxkY2pIm1GVMwKZ0nvnTqz/ZKLrou", "四方 謙三",
                    UsersPermission.User, true),
            new UsersEntity(19L, "nojiri",
                    "$2a$10$5YRzeddgE6s7cZUqcPNBw.Nqqy24/pFCOC0Bpu6hbakGuASJtyyMO", "野尻 欧子",
                    UsersPermission.User, true),
            new UsersEntity(10L, "yahata",
                    "$2a$10$ZlLj/QQ9ta0mnIFg3gFe4O8fZ3j2TgviSB/YW2IJKyBk24sfMHMZ6", "八幡 雅也",
                    UsersPermission.User, true),
            new UsersEntity(11L, "ichinohe",
                    "$2a$10$VcUwVqjSMnJxF.ColcRQEe4E1iUAwlSc8tmlcU2mV/50nt6xF2eUC", "一戸 敏雄",
                    UsersPermission.User, false),
            new UsersEntity(12L, "referencer",
                    "$2a$10$Nwig050LweltGBInbomL/ePMZ1Ofspepa.n7dnBJpQTBWDFtl/THO", "参照ユーザー",
                    UsersPermission.Referencer, true)));

    /**
     * 全件取得
     * 
     * @return テーブルの全データ
     */
    public List<UsersEntity> findAll() {
        return this.MOCK_DATA;
    }

    /**
     * IDを指定してユーザーを取得する
     * 
     * @param id ID
     * 
     * @return 指定した1件のデータ
     */
    public Optional<UsersEntity> findById(Long id) {
        return this.MOCK_DATA.stream().filter(row -> row.getId().equals(id)).findFirst();
    }

    /**
     * ユーザーの追加
     * 
     * @param user 挿入するユーザーデータ
     * 
     * @return 挿入した件数(= 1)
     */
    public Integer insertUser(UsersEntity user) {
        // 擬似的にID割り当てる
        user.setId(this.MOCK_DATA.stream()
                .max((row1, row2) -> Long.compare(row1.getId(), row2.getId())).get().getId() + 1);

        return 1;
    }
}
