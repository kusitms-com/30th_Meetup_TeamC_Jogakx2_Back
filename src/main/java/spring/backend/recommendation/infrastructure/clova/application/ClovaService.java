package spring.backend.recommendation.infrastructure.clova.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;
import spring.backend.recommendation.infrastructure.clova.dto.request.ClovaRequest;
import spring.backend.recommendation.infrastructure.clova.dto.response.ClovaResponse;

@Service
@RequiredArgsConstructor
@Log4j2
public class ClovaService {

    @Value("${clova.api.url}")
    private String apiUrl;

    private final WebClient clovaStudioWebClient;

    public ClovaResponse requestToClovaStudio(ClovaRecommendationRequest clovaRecommendationRequest) {
        try {
            ClovaRequest request = ClovaRequest.createClovaRequest(clovaRecommendationRequest);

            return clovaStudioWebClient.post()
                    .uri(apiUrl)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ClovaResponse.class)
                    .block();
        } catch (WebClientException e) {
            log.error("WebClient 에러 발생 - 에러 메시지: {}", e.getMessage(), e);
            throw GlobalErrorCode.WEB_CLIENT_ERROR.toException();
        } catch (Exception e) {
            log.error("알 수 없는 내부 오류 발생 - 에러 메시지: {}", e.getMessage(), e);
            throw GlobalErrorCode.INTERNAL_ERROR.toException();
        }
    }
}