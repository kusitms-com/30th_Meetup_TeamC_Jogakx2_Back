package spring.backend.recommendation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record RecommendationResponse(

        @Schema(description = "[OFFLINE, ONLINE_AND_OFFLINE] 오프라인 추천 응답")
        List<ClovaRecommendationResponse> offlineRecommendations,

        @Schema(description = "[ONLINE, ONLINE_AND_OFFLINE] 온라인 추천 응답")
        List<OpenAIRecommendationResponse> onlineRecommendations
) {
    public static RecommendationResponse of(List<ClovaRecommendationResponse> offlineRecommendations, List<OpenAIRecommendationResponse> onlineRecommendations) {
        return new RecommendationResponse(offlineRecommendations, onlineRecommendations);
    }
}
