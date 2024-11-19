package spring.backend.activity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Month;
import java.util.List;

public record MonthlyActivityOverviewResponse(
        @Schema(description = "유저의 가입년도", example = "2024")
        int joinedYear,

        @Schema(description = "유저의 가입월", example = "JANUARY")
        Month joinedMonth,

        @Schema(description = "이번 달 총 모은 자투리 시간(분단위)과 활동횟수")
        MonthlySavedTimeAndActivityCountResponse monthlySavedTimeAndActivityCount,

        @Schema(description = "Keyword 별 자투리 시간 및 활동 횟수 요약")
        List<MonthlyActivityCountByKeywordResponse> activitiesByKeywordSummary
) {
}
