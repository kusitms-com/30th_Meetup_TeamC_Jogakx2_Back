package spring.backend.activity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.domain.value.Type;

import java.util.Set;

public record UserActivitySelectRequest(
        @NotNull(message = "활동 유형은 필수 입력 항목입니다.")
        @Schema(description = "활동 유형 (ONLINE, OFFLINE, ONLINE_AND_OFFLINE)", example = "ONLINE")
        Type type,

        @NotNull(message = "자투리 시간은 필수 입력 항목입니다.")
        @Min(value = 10, message = "자투리 시간은 최소 10이어야 합니다.")
        @Max(value = 300, message = "자투리 시간은 최대 300이어야 합니다.")
        @Schema(description = "자투리 시간", example = "300")
        Integer spareTime,

        @NotNull(message = "키워드는 최대 5가지 입력 가능하며, 키워드를 선택하지 않은 경우 빈 배열을 보내주세요")
        @Schema(description = "키워드(SELF_DEVELOPMENT, HEALTH, NATURE, CULTURE_ART, ENTERTAINMENT, RELAXATION, SOCIAL)",
                example = "[{\"category\": \"SELF_DEVELOPMENT\", \"image\": \"image_url\"}, {\"category\": \"HEALTH\", \"image\": \"image_url\"}]")
        Set<Keyword> keywords,

        @NotNull(message = "타이틀은 필수 입력 항목입니다.")
        @Schema(description = "타이틀", example = "카페에서 커피 마시며 책 읽기")
        String title,

        @NotNull(message = "Content는 필수 입력 항목입니다.")
        @Schema(description = "내용", example = "조용한 카페에서 좋아하는 책을 읽으며 여유로운 시간을 즐길 수 있습니다.")
        String content,

        @Schema(description = "장소", example = "서울시 강남구 역삼동")
        String location
) {
}
