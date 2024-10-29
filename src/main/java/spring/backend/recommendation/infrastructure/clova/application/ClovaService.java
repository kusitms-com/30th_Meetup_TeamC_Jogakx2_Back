package spring.backend.recommendation.infrastructure.clova.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.recommendation.infrastructure.clova.dto.request.ClovaRequest;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;
import spring.backend.recommendation.infrastructure.clova.dto.response.ClovaResponse;
import spring.backend.recommendation.infrastructure.clova.exception.ClovaErrorCode;

@Service
@RequiredArgsConstructor
public class ClovaService {

    @Value("${clova.api.url}")
    private String apiUrl;

    private final WebClient webClient;

    public String requestToClovaStudio(ClovaRecommendationRequest userInputRequest) {
        try {
            ClovaRequest request = ClovaRequest.createClovaRequest(userInputRequest);

            ClovaResponse result = webClient.post()
                    .uri(apiUrl)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ClovaResponse.class)
                    .block();

            if (result == null || result.getResult() == null || result.getResult().getMessage() == null) {
                throw ClovaErrorCode.NULL_RESPONSE_FROM_CLOVA.toException();
            }

            return result.getResult().getMessage().getContent();
        } catch (DomainException e) {
            throw e;
        } catch (WebClientException e) {
            throw GlobalErrorCode.WEB_CLIENT_ERROR.toException();
        } catch (Exception e) {
            throw GlobalErrorCode.INTERNAL_ERROR.toException();
        }
    }
}