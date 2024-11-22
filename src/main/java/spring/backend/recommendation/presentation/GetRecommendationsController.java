package spring.backend.recommendation.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;
import spring.backend.recommendation.application.GetRecommendationsFromClovaService;
import spring.backend.recommendation.application.GetRecommendationsFromOpenAIService;
import spring.backend.recommendation.dto.request.AIRecommendationRequest;
import spring.backend.recommendation.dto.response.ClovaRecommendationResponse;
import spring.backend.recommendation.dto.response.OpenAIRecommendationResponse;
import spring.backend.recommendation.dto.response.RecommendationResponse;
import spring.backend.recommendation.presentation.swagger.GetRecommendationsSwagger;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class GetRecommendationsController implements GetRecommendationsSwagger {

    private final GetRecommendationsFromClovaService getRecommendationsFromClovaService;

    private final GetRecommendationsFromOpenAIService getRecommendationsFromOpenAIService;

    @Authorization
    @PostMapping("/v1/recommendations")
    public ResponseEntity<RestResponse<RecommendationResponse>> getRecommendations(@AuthorizedMember Member member, @Valid @RequestBody AIRecommendationRequest request) {
        List<ClovaRecommendationResponse> offlineRecommendations = new ArrayList<>();
        List<OpenAIRecommendationResponse> onlineRecommendations = new ArrayList<>();

        if (request.activityType().includesOffline()) {
            offlineRecommendations = getRecommendationsFromClovaService.getRecommendationsFromClova(request);
        }
        if (request.activityType().includesOnline()) {
            onlineRecommendations = getRecommendationsFromOpenAIService.getRecommendationsFromOpenAI(request);
        }

        RecommendationResponse recommendationResponse = RecommendationResponse.of(offlineRecommendations, onlineRecommendations);
        return ResponseEntity.ok(new RestResponse<>(recommendationResponse));
    }
}
