package spring.backend.recommendation.infrastructure.clova.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.recommendation.application.RecommendationProvider;
import spring.backend.recommendation.dto.request.AIRecommendationRequest;
import spring.backend.recommendation.infrastructure.clova.dto.request.ClovaRequest;
import spring.backend.recommendation.infrastructure.clova.dto.response.ClovaResponse;
import spring.backend.recommendation.infrastructure.clova.exception.ClovaErrorCode;

@Component
@RequiredArgsConstructor
@Log4j2
public class ClovaRecommendationProvider implements RecommendationProvider<ClovaResponse> {

    @Value("${clova.api.url}")
    private String apiUrl;

    private final WebClient clovaStudioWebClient;

    @Override
    public ClovaResponse getRecommendations(AIRecommendationRequest aiRecommendationRequest) {
        try {
            ClovaRequest request = ClovaRequest.createClovaRequest(aiRecommendationRequest);

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

//        if (result == null || result.getResult() == null || result.getResult().getMessage() == null || result.getResult().getMessage().getContent() == null) {
//            log.error("Clova 서비스로부터 null 응답을 수신했습니다. 요청 내용: {}", clovaRecommendationRequest);
//            throw ClovaErrorCode.NULL_RESPONSE_FROM_CLOVA.toException();
//        }
//
//        return result;
    }
}
