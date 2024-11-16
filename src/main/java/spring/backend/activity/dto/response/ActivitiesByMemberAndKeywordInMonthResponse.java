package spring.backend.activity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.activity.domain.value.Keyword;

import java.util.List;

public record ActivitiesByMemberAndKeywordInMonthResponse(
        @Schema(description = "이번 달 해당 키워드 활동을 통해 모은 자투리 시간(분단위)", example = "120")
        Long totalSavedTimeByKeywordInMonth,

        @Schema(description = "활동 키워드별 활동 목록")
        List<ActivityWithTitleAndSavedTimeResponse> activities,

        @Schema(description = "활동 키워드별 활동 총 개수")
        long totalActivityCountByKeywordInMonth,

        @Schema(description = "키워드 카테고리", example = "NATURE")
        Keyword keyword
) {
}
