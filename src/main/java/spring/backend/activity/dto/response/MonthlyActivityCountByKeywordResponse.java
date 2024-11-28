package spring.backend.activity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.activity.infrastructure.persistence.jpa.value.KeywordJpaValue;

public record MonthlyActivityCountByKeywordResponse(
        @Schema(description = "활동의 Keyword" , example = "{\"category\": \"SELF_DEVELOPMENT\", \"image\": \"https://example.com/image.jpg\"}")
        KeywordJpaValue keyword,

        @Schema(description = "Keyword별 활동 횟수" , example = "2")
        long activityCount
) {
}
