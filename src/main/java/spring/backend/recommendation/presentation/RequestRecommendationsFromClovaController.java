package spring.backend.recommendation.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.core.presentation.RestResponse;
import spring.backend.recommendation.application.GetRecommendationsFromClovaService;
import spring.backend.recommendation.dto.request.UserInputRequest;
import spring.backend.recommendation.dto.response.ClovaRecommendationResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/recommendations")
public class RequestRecommendationsFromClovaController {
    private final GetRecommendationsFromClovaService getRecommendationsFromClovaService;

    @PostMapping
    public ResponseEntity<RestResponse<List<ClovaRecommendationResponse>>> requestRecommendations(@RequestBody UserInputRequest userInputRequest) {
        List<ClovaRecommendationResponse> response = getRecommendationsFromClovaService.getRecommendationsFromClova(userInputRequest);
        return ResponseEntity.ok(new RestResponse<>(response));
    }
}