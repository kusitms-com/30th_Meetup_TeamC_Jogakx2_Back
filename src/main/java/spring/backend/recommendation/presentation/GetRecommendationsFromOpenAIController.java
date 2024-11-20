package spring.backend.recommendation.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;
import spring.backend.recommendation.application.GetRecommendationsFromOpenAIService;
import spring.backend.recommendation.dto.request.AIRecommendationRequest;
import spring.backend.recommendation.dto.response.OpenAIRecommendationResponse;
import spring.backend.recommendation.presentation.swagger.GetRecommendationsFromOpenAISwagger;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetRecommendationsFromOpenAIController implements GetRecommendationsFromOpenAISwagger {

    private final GetRecommendationsFromOpenAIService getRecommendationsFromOpenAIService;

    @Authorization
    @PostMapping("/v1/recommendations/open-ai")
    public ResponseEntity<RestResponse<List<OpenAIRecommendationResponse>>> GetRecommendationsFromOpenAI(@AuthorizedMember Member member, @RequestBody AIRecommendationRequest request) {
        List<OpenAIRecommendationResponse> recommendationsFromOpenAI = getRecommendationsFromOpenAIService.getRecommendationsFromOpenAI(request);
        return ResponseEntity.ok(new RestResponse<>(recommendationsFromOpenAI));
    }
}
