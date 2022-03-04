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
    public List<UsersEntity> findAll();

    /**
     * IDを指定してユーザーを取得する
     * 
     * @param id ID
     * 
     * @return 指定した1件のデータ
     */
    public Optional<UsersEntity> findById(Long id);

    /**
     * アカウントを指定してユーザーを取得する
     * 
     * @param account アカウント
     * 
     * @return 指定した1件のデータ
     */
    public Optional<UsersEntity> findByAccount(String account);

    /**
     * ユーザーの追加
     * 
     * @param user 挿入するユーザーデータ
     * 
     * @return 挿入した件数(= 1)
     */
    public Integer insertUser(UsersEntity user);
}
