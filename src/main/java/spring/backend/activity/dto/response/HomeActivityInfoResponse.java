package spring.backend.activity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.activity.infrastructure.persistence.jpa.value.KeywordJpaValue;

public record HomeActivityInfoResponse(

        @Schema(description = "활동 ID", example = "1")
        Long id,

        @Schema(description = "활동 키워드", example = "{\"category\": \"SELF_DEVELOPMENT\", \"image\": \"https://example.com/image.jpg\"}")
        KeywordJpaValue keyword,

        @Schema(description = "활동 제목", example = "마음의 편안을 가져다주는 명상음악 20분 듣기")
        String title,

        @Schema(description = "모은 시간", example = "60")
        Integer savedTime
) {
}
