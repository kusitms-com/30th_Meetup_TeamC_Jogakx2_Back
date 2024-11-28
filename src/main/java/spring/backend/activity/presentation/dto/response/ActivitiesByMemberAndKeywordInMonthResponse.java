package spring.backend.activity.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.dto.response.ActivityWithTitleAndSavedTimeResponse;

import java.util.List;

public record ActivitiesByMemberAndKeywordInMonthResponse(
        @Schema(description = "이번 달 해당 키워드 활동을 통해 모은 자투리 시간(분단위)", example = "120")
        long totalSavedTimeByKeywordInMonth,

        @Schema(description = "활동 키워드별 활동 총 개수")
        long totalActivityCountByKeywordInMonth,

        @Schema(description = "활동 키워드별 활동 목록")
        List<ActivityWithTitleAndSavedTimeResponse> activities,

        @Schema(description = "키워드", example = "{\"category\":\"RELAXATION\",\"image\":\"https://d1zjcuqflbd5k.cloudfront.net/files/acc_1160/0/1160-2019-07-02-14-07-52-0000.jpg\"}")
        Keyword keyword
) {
}
