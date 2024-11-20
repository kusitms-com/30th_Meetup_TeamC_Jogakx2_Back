package spring.backend.recommendation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import spring.backend.activity.domain.value.Keyword;

@Getter
@AllArgsConstructor
public class ClovaRecommendationResponse {
    @Schema(description = "추천 순서")
    private int order;
    @Schema(description = "추천 제목")
    private String title;
    @Schema(description = "장소 이름")
    private String placeName;
    @Schema(description = "장소의 x 좌표")
    private String mapx;
    @Schema(description = "장소의 y 좌표")
    private String mapy;
    @Schema(description = "장소의 카카오맵 url (카카오맵만 제공)")
    private String placeUrl;
    @Schema(description = "추천 부제목")
    private String content;
    @Schema(description = "추천 키워드")
    private Keyword.Category keywordCategory;
}
