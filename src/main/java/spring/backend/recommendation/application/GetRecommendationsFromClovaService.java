package spring.backend.recommendation.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.backend.recommendation.dto.response.ClovaRecommendationResponse;
import spring.backend.recommendation.infrastructure.clova.application.ClovaService;
import spring.backend.recommendation.dto.request.UserInputRequest;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class GetRecommendationsFromClovaService {
    private final ClovaService clovaService;

    public List<ClovaRecommendationResponse> getRecommendationsFromClova(UserInputRequest userInputRequest) {
        String result = clovaService.requestToClovaStudio(userInputRequest);
        String[] recommendations = result.split("\n");

        List<ClovaRecommendationResponse> clovaResponses = new ArrayList<>();

        for(int i = 0; i < recommendations.length; i++) {
            ClovaRecommendationResponse clovaResponse = new ClovaRecommendationResponse(i + 1, recommendations[i]);
            clovaResponses.add(clovaResponse);
        }

        return clovaResponses;
    }
}
