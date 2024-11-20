package spring.backend.activity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TotalSavedTimeAndActivityCountByKeywordInMonth(
        @Schema(description = "이번 달 해당 키워드 활동을 통해 모은 자투리 시간(분단위)", example = "120")
        long totalSavedTimeByKeywordInMonth,

        @Schema(description = "활동 키워드별 활동 총 개수", example = "12")
        long totalActivityCountByKeywordInMonth
) {
}
