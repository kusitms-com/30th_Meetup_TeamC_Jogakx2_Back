package spring.backend.recommendation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClovaRecommendationResponse {
    private Integer order;
    private String title;
    private String content;
}
