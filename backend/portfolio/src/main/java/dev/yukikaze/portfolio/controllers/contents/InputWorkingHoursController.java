package dev.yukikaze.portfolio.controllers.contents;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.yukikaze.portfolio.annotations.JwtCookie;
import dev.yukikaze.portfolio.annotations.Permission;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.objects.JwtPayload;
import dev.yukikaze.portfolio.services.WorkingHoursService;

/**
 * 勤怠入力画面 Controller
 */
@RestController
@RequestMapping("/contents/inputWorkingHours")
@Permission({ UsersPermission.Admin, UsersPermission.User })
public class InputWorkingHoursController {
    /**
     * WorkingHoursService
     */
    private WorkingHoursService workingHoursService;

    /**
     * コンストラクター
     *
     * @param workingHoursService WorkingHoursService
     */
    public InputWorkingHoursController(WorkingHoursService workingHoursService) {
        this.workingHoursService = workingHoursService;
    }

    /**
     * 勤怠情報取得
     *
     * @param jwtPayload Cookie情報
     * @param year       対象年度
     * @param month      対象月
     *
     * @return 勤怠情報
     */
    @GetMapping
    public ListResponse list(@JwtCookie JwtPayload jwtPayload, @RequestParam("year") Integer year,
            @RequestParam("month") Integer month) {
        try {
            List<ListWorkingHours> workingHours = this.workingHoursService.getWorkingHours(jwtPayload.id(), year, month)
                    .stream()
                    .map(v -> new ListWorkingHours(v.getWorkDay(), v.getStartTime(), v.getEndTime(), v.getMemo()))
                    .toList();

            return new ListResponse(workingHours);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "勤怠情報の取得に失敗しました。");
        }
    }

    /**
     * listメソッドのレスポンス
     */
    public record ListResponse(List<ListWorkingHours> workingHours) {
    }

    /**
     * listメソッドのレスポンス - workingHours
     */
    public record ListWorkingHours(Date workDay, Date startTime, Date endTime, String memo) {
    }
}
