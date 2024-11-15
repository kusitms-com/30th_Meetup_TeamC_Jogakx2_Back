package spring.backend.activity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.activity.domain.value.Keyword;

public record MonthlyActivityCountByKeywordResponse(
        @Schema(description = "활동의 Keyword" , example = "{\"category\": \"SELF_DEVELOPMENT\", \"image\": \"https://example.com/image.jpg\"}")
        Keyword keyword,

        @Schema(description = "Keyword별 활동 횟수" , example = "2")
        Long activityCount
) {
}
