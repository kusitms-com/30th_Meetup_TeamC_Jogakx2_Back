package spring.backend.recommendation.infrastructure.link.youtube;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.recommendation.infrastructure.link.LinkWebClient;
import spring.backend.recommendation.infrastructure.link.youtube.dto.response.YoutubeResponse;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Log4j2
public class YoutubeWebClient implements LinkWebClient<YoutubeResponse> {

    @Value("${youtube.api-key}")
    private String apiKey;

    @Value("${youtube.search-url}")
    private String searchUrl;

    @Override
    public YoutubeResponse search(String query) {
        try {
            return WebClient.create()
                    .get()
                    .uri(buildSearchUrl(query))
                    .retrieve()
                    .bodyToMono(YoutubeResponse.class)
                    .block();
        } catch (WebClientException e) {
            log.error("WebClient 에러 발생 - 에러 메시지: {}", e.getMessage(), e);
            throw GlobalErrorCode.WEB_CLIENT_ERROR.toException();
        } catch (Exception e) {
            log.error("알 수 없는 내부 오류 발생 - 에러 메시지: {}", e.getMessage(), e);
            throw GlobalErrorCode.INTERNAL_ERROR.toException();
        }
    }

    private URI buildSearchUrl(String query) {
        return UriComponentsBuilder.fromUriString(searchUrl)
                .queryParams(createSearchRequestParams(query))
                .build()
                .toUri();
    }

    private MultiValueMap<String, String> createSearchRequestParams(String query) {
        final String part = "snippet";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", apiKey);
        params.add("part", part);
        params.add("q", query);
        return params;
    }
}
