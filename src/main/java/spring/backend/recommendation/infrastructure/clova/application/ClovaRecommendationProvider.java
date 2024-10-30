package spring.backend.recommendation.infrastructure.clova.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;
import spring.backend.recommendation.application.RecommendationProvider;
import spring.backend.recommendation.infrastructure.clova.dto.response.ClovaResponse;
import spring.backend.recommendation.infrastructure.clova.exception.ClovaErrorCode;

@Component
@RequiredArgsConstructor
@Log4j2
public class ClovaRecommendationProvider implements RecommendationProvider {

    private final ClovaService clovaService;

    @Override
    public String requestToClovaStudio(ClovaRecommendationRequest clovaRecommendationRequest) {
        ClovaResponse result = clovaService.requestToClovaStudio(clovaRecommendationRequest);

        if (result == null || result.getResult() == null || result.getResult().getMessage() == null || result.getResult().getMessage().getContent() == null) {
            log.error("Clova 서비스로부터 null 응답을 수신했습니다. 요청 내용: {}", clovaRecommendationRequest);
            throw ClovaErrorCode.NULL_RESPONSE_FROM_CLOVA.toException();
        }

        return result.getResult().getMessage().getContent();
    }
}
