package dev.yukikaze.portfolio.mappers;

import java.util.List;

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
     * @return users テーブルの全データ
     */
    public List<UsersEntity> findAll();
}
