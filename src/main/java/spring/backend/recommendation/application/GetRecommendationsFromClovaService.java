package spring.backend.recommendation.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.backend.recommendation.dto.response.ClovaRecommendationResponse;
import spring.backend.recommendation.infrastructure.clova.application.ClovaService;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;
import spring.backend.recommendation.infrastructure.clova.dto.response.ClovaResponse;
import spring.backend.recommendation.infrastructure.clova.exception.ClovaErrorCode;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class GetRecommendationsFromClovaService {
    private static final String LF = "\n";

    private final ClovaService clovaService;

    public List<ClovaRecommendationResponse> getRecommendationsFromClova(ClovaRecommendationRequest clovaRecommendationRequest) {
        ClovaResponse result = clovaService.requestToClovaStudio(clovaRecommendationRequest);

        if (result == null || result.getResult() == null || result.getResult().getMessage() == null || result.getResult().getMessage().getContent() == null) {
            log.error("Clova 서비스로부터 null 응답을 수신했습니다. 요청 내용: {}", clovaRecommendationRequest);
            throw ClovaErrorCode.NULL_RESPONSE_FROM_CLOVA.toException();
        }

        return parseRecommendationsFromClova(result.getResult().getMessage().getContent());
    }

    private List<ClovaRecommendationResponse> parseRecommendationsFromClova(String content) {
        String[] recommendations = content.split(LF);

        List<ClovaRecommendationResponse> clovaResponses = new ArrayList<>();

        for (int i = 0; i < recommendations.length; i++) {
            clovaResponses.add(new ClovaRecommendationResponse(i + 1, recommendations[i]));
        }

        return clovaResponses;
    }
}
