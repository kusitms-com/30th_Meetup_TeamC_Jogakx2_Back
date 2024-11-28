package spring.backend.activity.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UserMonthlyActivitySummary(

        @Schema(description = "사용자 최초 가입 시간", example = "2024-11-14T06:10:55.091954")
        LocalDateTime registrationDate,

        @Schema(description = "해당 달에 모은 시간 조각의 합 (분 단위)", example = "120")
        long totalSavedTime,

        @Schema(description = "사용자가 해당 달에 한 활동의 총 횟수", example = "10")
        long monthlyActivityCount
) {
}
