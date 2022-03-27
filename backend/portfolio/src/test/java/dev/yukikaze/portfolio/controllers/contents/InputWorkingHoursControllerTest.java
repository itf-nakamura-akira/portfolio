package dev.yukikaze.portfolio.controllers.contents;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.yukikaze.portfolio.controllers.contents.InputWorkingHoursController.ListResponse;
import dev.yukikaze.portfolio.controllers.contents.InputWorkingHoursController.ListWorkingHours;
import dev.yukikaze.portfolio.entities.WorkingHoursEntity;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.mappers.WorkingHoursMapper;
import dev.yukikaze.portfolio.mappers.WorkingHoursMapperMock;
import dev.yukikaze.portfolio.mappers.WorkingHoursMapperTest;
import dev.yukikaze.portfolio.objects.JwtPayload;
import dev.yukikaze.portfolio.services.WorkingHoursService;

/**
 * InputWorkingHoursController のテストコード
 */
@DisplayName("InputWorkingHoursController のテストコード")
public class InputWorkingHoursControllerTest {
    /**
     * InputWorkingHoursController
     */
    private InputWorkingHoursController inputWorkingHoursController;

    /**
     * WorkingHoursMapper(assert用)
     */
    private WorkingHoursMapper workingHoursMapper;

    /**
     * 初期化
     */
    @BeforeEach
    public void beforeEach() {
        this.workingHoursMapper = new WorkingHoursMapperMock();
        this.inputWorkingHoursController = new InputWorkingHoursController(
                new WorkingHoursService(this.workingHoursMapper));
    }

    @Test
    @DisplayName("list メソッドのテスト")
    public void list() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        int targetYear = calendar.getWeekYear();
        int targetMonth = calendar.get(Calendar.MONTH) + 1;

        // 勤怠情報の取得
        var jwtPayload = new JwtPayload(1L, UsersPermission.Admin, new Date().getTime() / 1000 + 60 * 1000);
        ListResponse response = this.inputWorkingHoursController.list(jwtPayload, targetYear, targetMonth);
        List<ListWorkingHours> workingHours = response.workingHours();

        WorkingHoursMapperTest.assertSelect(workingHours.stream()
                .map(v -> new WorkingHoursEntity(1L, 1L, v.workDay(), v.startTime(), v.endTime(), v.memo())).toList(),
                targetYear, targetMonth - 1/* Javaの月は0始まり */);
    }
}
