package spring.backend.recommendation.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.backend.recommendation.dto.response.ClovaRecommendationResponse;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class GetRecommendationsFromClovaService {
    private static final String LINE_SEPARATOR = "\n";

    private final RecommendationProvider recommendationProvider;

    public List<ClovaRecommendationResponse> getRecommendationsFromClova(ClovaRecommendationRequest clovaRecommendationRequest) {

        String[] recommendations = recommendationProvider.requestToClovaStudio(clovaRecommendationRequest).split(LINE_SEPARATOR);

        List<ClovaRecommendationResponse> clovaResponses = new ArrayList<>();

        for (int i = 0; i < recommendations.length; i++) {
            clovaResponses.add(new ClovaRecommendationResponse(i + 1, recommendations[i]));
        }

        return clovaResponses;
    }
}
