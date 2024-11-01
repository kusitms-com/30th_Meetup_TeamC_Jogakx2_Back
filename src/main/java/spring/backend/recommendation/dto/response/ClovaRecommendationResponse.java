package spring.backend.recommendation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spring.backend.activity.domain.value.Keyword;

@Getter
@AllArgsConstructor
public class ClovaRecommendationResponse {
    private Integer order;
    private String title;
    private String content;
    private Keyword.Category keywordCategory;
}
