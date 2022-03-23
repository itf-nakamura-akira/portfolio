package dev.yukikaze.portfolio.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import dev.yukikaze.portfolio.entities.WorkingHoursEntity;

/**
 * working_hours テーブルの問い合わせインターフェイス
 */
@Mapper
public interface WorkingHoursMapper {
    /**
     * 労働時間データを取得する
     *
     * @param userId ユーザーID
     * @param year   対象年
     * @param month  対象月
     *
     * @return 労働時間データ
     */
    public List<WorkingHoursEntity> select(Long userId, Integer year, Integer month);
}
