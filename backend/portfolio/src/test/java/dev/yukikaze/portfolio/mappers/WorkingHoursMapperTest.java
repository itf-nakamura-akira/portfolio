package dev.yukikaze.portfolio.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import dev.yukikaze.portfolio.entities.WorkingHoursEntity;

/**
 * WorkingHoursMapper のテストコード
 */
@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("WorkingHoursMapper のテストコード")
public class WorkingHoursMapperTest {
    /**
     * WorkingHoursMapper
     */
    @Autowired
    private WorkingHoursMapper workingHoursMapper;

    @Test
    @DisplayName("select メソッドのテスト")
    public void select() {
        List<WorkingHoursEntity> workingHoursList;
        Calendar calendar;

        // 前月から1年分の確認
        calendar = Calendar.getInstance();

        for (int i = 1; i < 12; i++) {
            calendar.add(Calendar.MONTH, -1);
            int targetYear = calendar.get(Calendar.YEAR);
            int targetMonth = calendar.get(Calendar.MONTH);

            // データの取得
            workingHoursList = this.workingHoursMapper.select(1L, targetYear, targetMonth + 1/* Javaの月は0始まり */);

            // データの確認
            assertSelect(workingHoursList, targetYear, targetMonth);
        }

        // 別ユーザーの確認
        calendar = Calendar.getInstance();

        for (int i = 1; i < 12; i++) {
            calendar.add(Calendar.MONTH, -1);
            int targetYear = calendar.get(Calendar.YEAR);
            int targetMonth = calendar.get(Calendar.MONTH);

            // データの取得
            workingHoursList = this.workingHoursMapper.select(10L, targetYear, targetMonth + 1/* Javaの月は0始まり */);

            // データの確認
            assertSelect(workingHoursList, targetYear, targetMonth);
        }

        // 存在しないユーザーの指定
        calendar = Calendar.getInstance();
        workingHoursList = this.workingHoursMapper.select(-1L, calendar.get(Calendar.YEAR) - 1,
                calendar.get(Calendar.MONTH));

        // 件数の確認
        assertEquals(0, workingHoursList.size());
    }

    /**
     * selectのassert関数
     *
     * @param workingHoursList 確認するデータ
     * @param year             対象年
     * @param month            対象月
     */
    public static void assertSelect(List<WorkingHoursEntity> workingHoursList, int year, int month) {
        var assertDateList = new ArrayList<Date>();

        // 月曜～金曜のデータ取得(テストデータがそう登録されているため)
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);

        while (calendar.get(Calendar.MONTH) == month) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek != Calendar.SUNDAY && dayOfWeek != Calendar.SATURDAY) {
                assertDateList.add(calendar.getTime());
            }

            calendar.add(Calendar.DATE, 1);
        }

        // 件数の確認
        assertEquals(workingHoursList.size(), assertDateList.size());

        // 昇順でソートされているか
        for (int i = 0; i < assertDateList.size(); i++) {
            WorkingHoursEntity workingHour = workingHoursList.get(i);
            Date assertDate = assertDateList.get(i);
            Calendar whCalendar = Calendar.getInstance();
            Calendar assertCalendar = Calendar.getInstance();

            whCalendar.setTime(workingHour.getWorkDay());
            assertCalendar.setTime(assertDate);

            assertTrue(whCalendar.get(Calendar.YEAR) == assertCalendar.get(Calendar.YEAR));
            assertTrue(whCalendar.get(Calendar.MONTH) == assertCalendar.get(Calendar.MONTH));
            assertTrue(whCalendar.get(Calendar.DATE) == assertCalendar.get(Calendar.DATE));
        }
    }
}
