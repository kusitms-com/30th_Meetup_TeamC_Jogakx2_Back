package spring.backend.recommendation.infrastructure.clova.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import spring.backend.core.exception.DomainException;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.recommendation.infrastructure.clova.dto.request.ClovaRequest;
import spring.backend.recommendation.dto.request.UserInputRequest;
import spring.backend.recommendation.infrastructure.clova.dto.response.ClovaResponse;

@Service
@RequiredArgsConstructor
public class ClovaService {

    @Value("${clova.api.url}")
    private String apiUrl;

    @Value("${clova.api.api-key}")
    private String apiKey;

    @Value("${clova.api.api-gateway-key}")
    private String apiGatewayKey;

    private final WebClient webClient;

    public String requestToClovaStudio(UserInputRequest userInputRequest) {
        try {
            ClovaRequest request = ClovaRequest.createClovaRequest(userInputRequest);

            ClovaResponse result = webClient.post()
                    .uri(apiUrl)
                    .headers(httpHeaders -> {
                        httpHeaders.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
                        httpHeaders.set("X-NCP-APIGW-API-KEY", apiGatewayKey);
                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    })
                    .body(Mono.just(request), ClovaRequest.class)
                    .retrieve()
                    .bodyToMono(ClovaResponse.class)
                    .block();

            assert result != null;

            return result.getResult().getMessage().getContent();
        } catch (DomainException e) {
            throw e;
        }
        catch (Exception e) {
            throw GlobalErrorCode.INTERNAL_ERROR.toException();
        }
    }
}