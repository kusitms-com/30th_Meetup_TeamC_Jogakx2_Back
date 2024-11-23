package spring.backend.recommendation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.activity.domain.value.Keyword;

public record OpenAIRecommendationResponse(

        @Schema(description = "추천 결과 순서", example = "1")
        int order,

        @Schema(description = "직접적 추천 활동", example = "마음의 편안을 가져다주는 명상 음악 20분 듣기")
        String title,

        @Schema(description = "꾸밈글", example = "휴식에는 역시 명상이 최고!")
        String content,

        @Schema(description = "활동 키워드", example = "{\n" +
                "          \"category\": \"SELF_DEVELOPMENT\",\n" +
                "          \"image\": \"images/self_development.png\"\n" +
                "        }")
        Keyword keyword,

        @Schema(description = "외부 링크", example = "https://www.youtube.com")
        String url
) {
    public static OpenAIRecommendationResponse of(int order, String title, String content, Keyword keyword, String url) {
        return new OpenAIRecommendationResponse(order, title, content, keyword, url);
    }
}
