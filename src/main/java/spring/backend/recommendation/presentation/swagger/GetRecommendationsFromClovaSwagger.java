package spring.backend.recommendation.presentation.swagger;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;
import spring.backend.recommendation.dto.response.ClovaRecommendationResponse;
import spring.backend.recommendation.infrastructure.clova.exception.ClovaErrorCode;

import java.util.List;

@Tag(name = "Recommendation", description = "추천")
public interface GetRecommendationsFromClovaSwagger {

    @Operation(
            summary = "사용자 추천 요청 API",
            description = "사용자가 활동 추천을 요청합니다.",
            operationId = "/v1/recommendations"
    )
    @ApiErrorCode({GlobalErrorCode.class, ClovaErrorCode.class})
    ResponseEntity<RestResponse<List<ClovaRecommendationResponse>>> requestRecommendations(@Parameter(hidden = true) Member member, ClovaRecommendationRequest clovaRecommendationRequest);
}
