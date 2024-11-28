package spring.backend.activity.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ReadActivityCalendarRequest(

        @Min(value = 2024, message = "년도는 2024년 이상이어야 합니다.")
        @Schema(description = "캘린더 조회 년도", example = "2024")
        int year,

        @Min(value = 1, message = "월은 1~12 사이여야 합니다.")
        @Max(value = 12, message = "월은 1~12 사이여야 합니다.")
        @Schema(description = "캘린더 조회 월", example = "11")
        int month
) {
}
