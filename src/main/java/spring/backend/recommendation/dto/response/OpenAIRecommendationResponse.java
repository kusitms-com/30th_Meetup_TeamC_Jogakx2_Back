package spring.backend.recommendation.dto.response;

import spring.backend.activity.domain.value.Keyword.Category;

public record OpenAIRecommendationResponse(

        int order,

        String title,

        String content,

        Category keywordCategory,

        String url
) {
    public static OpenAIRecommendationResponse of(int order, String title, String content, Category category, String url) {
        return new OpenAIRecommendationResponse(order, title, content, category, url);
    }
}
