package spring.backend.activity.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import spring.backend.activity.domain.value.Keyword;

public record ActivitiesByMemberAndKeywordInMonthRequest(
        @Min(value = 2024, message = "년도는 2024년 이후 값이어야 합니다.")
        int year,

        @Min(value = 1, message = "월은 1월과 12월 사이 값이어야 합니다.")
        @Max(value = 12, message = "월은 1월과 12월 사이 값이어야 합니다.")
        int month,

        @NotNull(message = "키워드 카테고리는 필수 값입니다.")
        @Schema(description = "키워드 카테고리 (예: HEALTH, SELF_DEVELOPMENT, CULTURE_ART, ENTERTAINMENT, RELAXATION, SOCIAL)", example = "RELAXATION", allowableValues = {
                "SELF_DEVELOPMENT", "HEALTH", "CULTURE_ART",
                "ENTERTAINMENT", "RELAXATION", "SOCIAL"
        })
        Keyword.Category keywordCategory
) {
}
