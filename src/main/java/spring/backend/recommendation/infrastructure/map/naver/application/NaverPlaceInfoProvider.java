package spring.backend.recommendation.infrastructure.map.naver.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spring.backend.recommendation.application.PlaceInfoProvider;
import spring.backend.recommendation.infrastructure.map.naver.dto.response.NaverMapResponse;
import spring.backend.recommendation.infrastructure.map.naver.exception.NaverMapErrorCode;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
@RequiredArgsConstructor
public class NaverPlaceInfoProvider implements PlaceInfoProvider {
    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Value("${naver.map.base-uri}")
    private String baseUri;

    private final ObjectMapper objectMapper;

    @Override
    public NaverMapResponse search(String query) {
        String encodedQuery = encodeSearchQuery(query);
        String apiUrl = baseUri + "?query=" + encodedQuery;
        Map<String, String> requestHeaders = createHeaders();

        String responseBody = fetchResponse(apiUrl, requestHeaders);
        return parseResponse(responseBody);
    }

    private Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Naver-Client-Id", clientId);
        headers.put("X-Naver-Client-Secret", clientSecret);
        return headers;
    }

    private String encodeSearchQuery(String query) {
        return URLEncoder.encode(query, StandardCharsets.UTF_8);
    }

    private String fetchResponse(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection connection = createConnection(apiUrl);
        try {
            connection.setRequestMethod("GET");
            requestHeaders.forEach(connection::setRequestProperty);

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK
                    ? readStream(connection.getInputStream())
                    : readStream(connection.getErrorStream());
        } catch (IOException e) {
            log.error(
                    "API 요청과 응답이 실패했습니다. - 에러 메시지: {}",
                    e.getMessage(),
                    e
            );
            throw NaverMapErrorCode.API_REQUEST_FAILED.toException();
        } finally {
            connection.disconnect();
        }
    }

    private HttpURLConnection createConnection(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            log.error(
                    "API 연결에 실패했습니다. - 에러 메시지: {}",
                    e.getMessage(),
                    e
            );
            throw NaverMapErrorCode.FAILED_TO_CONNECT_API.toException();
        }
    }

    private String readStream(InputStream stream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (IOException e) {
            log.error(
                    "API 응답을 읽는데 실패했습니다. - 에러 메시지: {}",
                    e.getMessage(),
                    e
            );
            throw NaverMapErrorCode.FAILED_TO_READ_RESPONSE.toException();
        }
    }

    private NaverMapResponse parseResponse(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, NaverMapResponse.class);
        } catch (IOException e) {
            log.error("응답을 파싱하는데 실패했습니다. - 에러 메시지: {}", e.getMessage(), e);
            throw NaverMapErrorCode.FAILED_TO_PARSE_RESPONSE.toException();
        }
    }
}
