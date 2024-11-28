package spring.backend.activity.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.activity.domain.value.Keyword.Category;

import java.time.LocalDateTime;

public record UserMonthlyActivityDetail(

        @Schema(description = "활동 카테고리 \n\n SELF_DEVELOPMENT(자기개발), HEALTH(건강), CULTURE_ART(문화/예술), ENTERTAINMENT(엔터테인먼트), RELAXATION(휴식), SOCIAL(소셜)",
                example = "SELF_DEVELOPMENT")
        Category category,

        @Schema(description = "활동 제목", example = "마음의 편안을 가져다주는 명상음악 20분 듣기")
        String title,

        @Schema(description = "모은 시간", example = "20")
        int savedTime,

        @Schema(description = "활동 생성 시간", example = "2024-11-16T14:24:08.548712")
        LocalDateTime activityCreatedAt
) {
}
