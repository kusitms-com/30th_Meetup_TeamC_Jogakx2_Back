package spring.backend.recommendation.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;
import spring.backend.recommendation.dto.request.AIRecommendationRequest;
import spring.backend.recommendation.dto.response.OpenAIRecommendationResponse;
import spring.backend.recommendation.infrastructure.openai.exception.OpenAIErrorCode;

import java.util.List;

@Tag(name = "Recommendation", description = "추천")
public interface GetRecommendationsFromOpenAISwagger {

    @Operation(
            summary = "[ONLINE, ONLINE_AND_OFFLINE] 사용자 추천 요청 API",
            description = "[ONLINE, ONLINE_AND_OFFLINE] 사용자가 활동 추천을 요청합니다.",
            operationId = "/v1/recommendations/open-ai"
    )
    @ApiErrorCode({GlobalErrorCode.class, OpenAIErrorCode.class})
    ResponseEntity<RestResponse<List<OpenAIRecommendationResponse>>> GetRecommendationsFromOpenAI(@Parameter(hidden = true) Member member, AIRecommendationRequest request);
}
