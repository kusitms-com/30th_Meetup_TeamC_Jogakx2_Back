package spring.backend.recommendation.infrastructure.map.kakao.application;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import spring.backend.recommendation.application.PlaceInfoProvider;
import spring.backend.recommendation.infrastructure.map.kakao.dto.response.KakaoMapResponse;
import spring.backend.recommendation.infrastructure.map.kakao.exception.KakaoMapErrorCode;

@Component
@Log4j2
public class KakaoPlaceInfoProvider implements PlaceInfoProvider<KakaoMapResponse> {

    @Value("${kakao.rest-api-key}")
    private String restApiKey;

    private static final String BASE_URL = "https://dapi.kakao.com";
    private static final String SEARCH_PATH = "/v2/local/search/keyword.json";
    private static final String QUERY = "query";
    private static final String AUTHORIZATION = "Authorization";
    private static final String KAKAO_AK = "KakaoAK ";

    private final WebClient webClient;

    public KakaoPlaceInfoProvider() {
        this.webClient = WebClient.create(BASE_URL);
    }

    @Override
    public KakaoMapResponse search(String query) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(SEARCH_PATH)
                            .queryParam(QUERY, query)
                            .build())
                    .header(AUTHORIZATION, KAKAO_AK + restApiKey)
                    .retrieve()
                    .bodyToMono(KakaoMapResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("카카오 지도 API 응답에 오류가 발생했습니다. 상태 코드: {} , 응답 본문: {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw KakaoMapErrorCode.RESPONSE_ERROR.toException();
        } catch (Exception e) {
            log.error("장소 검색 중 예상치 못한 오류가 발생했습니다.", e);
            throw KakaoMapErrorCode.UNKNOWN_ERROR.toException();
        }
    }
}
