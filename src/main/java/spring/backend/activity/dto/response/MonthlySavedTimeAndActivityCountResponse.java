package spring.backend.activity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record MonthlySavedTimeAndActivityCountResponse(
        @Schema(description = "이번 달 총 모은 자투리 시간(분단위)", example = "120")
        long monthlyTotalSavedTime,

        @Schema(description = "이번 달 총 활동 횟수", example = "2")
        long monthlyTotalActivityCount
) {
}
