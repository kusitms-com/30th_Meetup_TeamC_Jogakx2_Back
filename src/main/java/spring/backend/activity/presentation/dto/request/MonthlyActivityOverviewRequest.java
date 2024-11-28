package spring.backend.activity.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record MonthlyActivityOverviewRequest(
        @Min(value = 2024, message = "년도는 2024년 이후 값이어야 합니다.")
        int year,

        @Min(value = 1, message = "월은 1월과 12월 사이 값이어야 합니다.")
        @Max(value = 12, message = "월은 1월과 12월 사이 값이어야 합니다.")
        int month
) {
}
