package spring.backend.recommendation.application;

import spring.backend.recommendation.presentation.dto.request.AIRecommendationRequest;

public interface RecommendationProvider<T> {
    T getRecommendations(AIRecommendationRequest aiRecommendationRequest);
}
