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

        int order = 1;

        for (int i = 0; i < recommendations.length; i++) {
            String line = recommendations[i].trim();

            if (line.matches("^\\d+\\. title :.*")) {
                String title = line.replaceFirst("^\\d+\\. title :", "").trim();
                String content = "";

                if (i + 1 < recommendations.length && recommendations[i + 1].trim().startsWith("content :")) {
                    content = recommendations[i + 1].trim().replaceFirst("^content :", "").trim();
                    i++;
                }

                clovaResponses.add(new ClovaRecommendationResponse(order, title, content));
                order++;
            }
        }

        return clovaResponses;
    }
}
