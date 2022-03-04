package dev.yukikaze.portfolio.mappers;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import dev.yukikaze.portfolio.entities.UsersEntity;

/**
 * users テーブルの問い合わせインターフェイス
 */
@Mapper
public interface UsersMapper {
    /**
     * 全件取得
     * 
     * @return テーブルの全データ
     */
    public List<UsersEntity> selectAll();

    /**
     * IDを指定してユーザーを取得する
     * 
     * @param id ID
     * 
     * @return 指定した1件のデータ
     */
    public Optional<UsersEntity> selectById(Long id);

    /**
     * アカウントを指定してユーザーを取得する
     * 
     * @param account アカウント
     * 
     * @return 指定した1件のデータ
     */
    public Optional<UsersEntity> selectByAccount(String account);

    /**
     * ユーザーの追加
     * 
     * @param user 挿入するユーザーデータ
     */
    public void insertUser(UsersEntity user);

    /**
     * ユーザーの更新
     * 
     * @param user 更新するユーザーデータ
     * 
     * @return 1件以上更新されたらtrue 0件だとfalse
     */
    public boolean updateUser(UsersEntity user);

    /**
     * パスワードの更新
     * 
     * @param id ID
     * @param passwordHash パスワードハッシュ
     * 
     * @return 1件以上更新されたらtrue 0件だとfalse
     */
    public boolean updateUserPassword(Long id, String passwordHash);
}
