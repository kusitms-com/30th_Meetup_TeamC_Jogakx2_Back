package spring.backend.recommendation.application;

import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;

public interface RecommendationProvider {
    String requestToClovaStudio(ClovaRecommendationRequest clovaRecommendationRequest);
}
