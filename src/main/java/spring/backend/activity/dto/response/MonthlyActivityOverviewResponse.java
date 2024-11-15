package spring.backend.activity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MonthlyActivityOverviewResponse(
        @Schema(description = "이번 달 총 모은 자투리 시간(분단위)과 활동횟수")
        @JsonProperty("monthlySavedTimeAndActivityCount")
        MonthlySavedTimeAndActivityCountResponse monthlySavedTimeAndActivityCountResponse,

        @Schema(description = "Keyword 별 자투리 시간 및 활동 횟수 요약")
        @JsonProperty("activitiesByKeywordSummary")
        List<MonthlyActivityCountByKeywordResponse> activitiesByKeywordSummaryResponses
) {
}
