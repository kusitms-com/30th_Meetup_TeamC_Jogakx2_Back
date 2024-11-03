package spring.backend.recommendation.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;
import spring.backend.recommendation.application.GetRecommendationsFromClovaService;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;
import spring.backend.recommendation.dto.response.ClovaRecommendationResponse;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/recommendations")
public class GetRecommendationsFromClovaController {
    private final GetRecommendationsFromClovaService getRecommendationsFromClovaService;

    @Authorization
    @PostMapping
    public ResponseEntity<RestResponse<List<ClovaRecommendationResponse>>> requestRecommendations(@AuthorizedMember Member member, @Valid @RequestBody ClovaRecommendationRequest clovaRecommendationRequest) {
        List<ClovaRecommendationResponse> response = getRecommendationsFromClovaService.getRecommendationsFromClova(clovaRecommendationRequest);
        return ResponseEntity.ok(new RestResponse<>(response));
    }
}