package spring.backend.activity.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MonthlyActivityOverviewRequest(
        @NotNull(message = "년도는 필수 입력 값입니다.")
        @Min(value = 2024, message = "년도는 2024년 이후 값이어야 합니다.")
        int year,

        @NotNull(message = "월은 필수 입력 값입니다.")
        @Min(value = 1, message = "월은 1월과 12월 사이 값이어야 합니다.")
        @Max(value = 12, message = "월은 1월과 12월 사이 값이어야 합니다.")
        int month
) {
}
