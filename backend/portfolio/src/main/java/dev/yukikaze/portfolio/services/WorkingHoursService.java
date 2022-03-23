package dev.yukikaze.portfolio.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.yukikaze.portfolio.entities.WorkingHoursEntity;
import dev.yukikaze.portfolio.mappers.WorkingHoursMapper;

/**
 * working_hours テーブルに関するビジネスロジック
 */
@Service
@Transactional
public class WorkingHoursService {
    /**
     * WorkingHoursMapper
     */
    private final WorkingHoursMapper workingHoursMapper;

    /**
     * コンストラクター
     *
     * @param workingHoursMapper workingHoursMapper
     */
    public WorkingHoursService(WorkingHoursMapper workingHoursMapper) {
        this.workingHoursMapper = workingHoursMapper;
    }

    /**
     * 労働時間データを取得する
     *
     * @param userId ユーザーID
     * @param year   対象年
     * @param month  対象月
     *
     * @return 労働時間データ
     */
    public List<WorkingHoursEntity> getWorkingHours(Long userId, Integer year, Integer month) {
        List<WorkingHoursEntity> workingHoursList = this.workingHoursMapper.select(userId, year, month);

        return workingHoursList;
    }
}
