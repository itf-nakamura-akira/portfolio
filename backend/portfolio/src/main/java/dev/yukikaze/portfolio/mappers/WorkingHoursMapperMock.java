package dev.yukikaze.portfolio.mappers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import dev.yukikaze.portfolio.entities.WorkingHoursEntity;

/**
 * WorkingHoursMapper のモック
 */
@Mapper
public class WorkingHoursMapperMock implements WorkingHoursMapper {
    /**
     * モックデータ
     */
    private final List<WorkingHoursEntity> mockData = this.generateMockData();

    /**
     * 労働時間データを取得する
     *
     * @param userId ユーザーID
     * @param year   対象年
     * @param month  対象月
     *
     * @return 労働時間データ
     */
    public List<WorkingHoursEntity> select(Long userId, Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date targetDateStart = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        Date targetDateEnd = calendar.getTime();

        return this.mockData.stream().filter(v -> v.getUsersId().equals(userId))
                .filter(v -> targetDateStart.getTime() <= v.getWorkDay().getTime())
                .filter(v -> v.getWorkDay().getTime() < targetDateEnd.getTime())
                .sorted((a, b) -> a.getWorkDay().before(b.getWorkDay()) ? -1 : 1).toList();
    }

    /**
     * モックデータを生成するメソッド
     *
     * @return モックデータ
     */
    private List<WorkingHoursEntity> generateMockData() {
        var mockData = new ArrayList<WorkingHoursEntity>();
        Long id = 1L;

        // データ期間
        for (int i = 0; i < 730; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -i);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            // 土曜・日曜のデータは作成しない(テストデータがそう登録されているため)
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
                continue;
            }

            Date workDay = calendar.getTime();
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            Date startTime = calendar.getTime();
            calendar.set(Calendar.HOUR_OF_DAY, 18);
            Date endTime = calendar.getTime();

            // ユーザー
            for (Long j = 1L; j <= 728; j++) {
                var data = new WorkingHoursEntity(id++, j, workDay, startTime, endTime, "memo: " + workDay.toString());
                mockData.add(data);
            }
        }

        return mockData;
    }
}
