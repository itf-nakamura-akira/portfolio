package dev.yukikaze.portfolio.services;

import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.yukikaze.portfolio.entities.WorkingHoursEntity;
import dev.yukikaze.portfolio.mappers.WorkingHoursMapperMock;
import dev.yukikaze.portfolio.mappers.WorkingHoursMapperTest;

/**
 * WorkingHoursService のテストコード
 */
@DisplayName("WorkingHoursService のテストコード")
public class WorkingHoursServiceTest {
    /**
     * WorkingHoursService
     */
    private WorkingHoursService workingHoursService;

    /**
     * 初期化
     */
    @BeforeEach
    public void beforeEach() {
        this.workingHoursService = new WorkingHoursService(new WorkingHoursMapperMock());
    }

    @Test
    @DisplayName("getWorkingHours メソッドのテスト")
    public void getWorkingHours() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        int targetYear = calendar.getWeekYear();
        int targetMonth = calendar.get(Calendar.MONTH) + 1;

        // 労働時間データの取得
        List<WorkingHoursEntity> workingHoursList = this.workingHoursService.getWorkingHours(1L, targetYear,
                targetMonth);

        // データの確認
        WorkingHoursMapperTest.assertSelect(workingHoursList, targetYear, targetMonth - 1/* Javaの月は0始まり */);
    }
}
