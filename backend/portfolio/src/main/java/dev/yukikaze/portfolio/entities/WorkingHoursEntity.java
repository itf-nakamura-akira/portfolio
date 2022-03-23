package dev.yukikaze.portfolio.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * working_hours テーブルのEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHoursEntity {
    /**
     * ID
     */
    private Long id;

    /**
     * ユーザーテーブルID
     */
    private Long usersId;

    /**
     * 出勤日
     */
    private Date workDay;

    /**
     * 開始時刻
     */
    private Date startTime;

    /**
     * 終了時刻
     */
    private Date endTime;

    /**
     * 備考
     */
    private String memo;
}
