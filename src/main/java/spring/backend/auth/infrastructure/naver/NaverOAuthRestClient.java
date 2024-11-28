package spring.backend.auth.infrastructure.naver;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import spring.backend.auth.presentation.dto.response.OAuthAccessTokenResponse;
import spring.backend.auth.presentation.dto.response.OAuthResourceResponse;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.auth.infrastructure.OAuthRestClient;
import spring.backend.auth.infrastructure.naver.dto.NaverResourceResponse;
import spring.backend.core.configuration.property.oauth.NaverOAuthProperty;

import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
@Log4j2
public class NaverOAuthRestClient implements OAuthRestClient {

    private static final String GRANT_TYPE = "authorization_code";

    private final NaverOAuthProperty naverOAuthProperty;

    @Override
    public URI getAuthUrl() {
        return UriComponentsBuilder.fromUriString(naverOAuthProperty.getAuthUri())
                .queryParam("client_id", naverOAuthProperty.getClientId())
                .queryParam("redirect_uri", naverOAuthProperty.getRedirectUri())
                .queryParam("response_type", "code")
                .queryParam("state", generateState())
                .build()
                .toUri();
    }

    @Override
    public OAuthAccessTokenResponse getAccessToken(String authCode, String state) {
        if (authCode == null || authCode.isEmpty() || state == null || state.isEmpty()) {
            log.error("[NaverOAuthProperty] authCode is null");
            throw AuthenticationErrorCode.NOT_EXIST_AUTH_CODE.toException();
        }
        try {
            return RestClient.create()
                    .post()
                    .uri(naverOAuthProperty.getTokenUri())
                    .headers(header -> header.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .body(createAccessTokenRequestBody(authCode, state))
                    .retrieve()
                    .body(OAuthAccessTokenResponse.class);
        } catch (Exception e) {
            log.error("[NaverOAuthProperty] error", e);
            throw AuthenticationErrorCode.ACCESS_TOKEN_NOT_ISSUED.toException();
        }
    }

    @Override
    public OAuthResourceResponse getResource(String oauthToken) {
        if (oauthToken == null || oauthToken.isEmpty()) {
            log.error("[NaverOAuthProperty] oauthToken is null");
            throw AuthenticationErrorCode.NOT_EXIST_AUTH_CODE.toException();
        }
        try {
            NaverResourceResponse naverResourceResponse = RestClient.create()
                    .get()
                    .uri(naverOAuthProperty.getResourceUri())
                    .headers(header -> {
                        header.setBearerAuth(oauthToken);
                        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    })
                    .retrieve()
                    .body(NaverResourceResponse.class);

            if (naverResourceResponse == null) {
                throw AuthenticationErrorCode.NOT_EXIST_RESOURCE_RESPONSE.toException();
            }
            NaverResourceResponse.Response resourceResponse = naverResourceResponse.getResponse();
            if (resourceResponse == null || resourceResponse.getId() == null || resourceResponse.getName() == null || resourceResponse.getEmail() == null) {
                throw AuthenticationErrorCode.NOT_EXIST_RESOURCE_RESPONSE.toException();
            }
            return OAuthResourceResponse.builder()
                    .id(resourceResponse.getId())
                    .name(resourceResponse.getName())
                    .email(resourceResponse.getEmail())
                    .build();
        } catch (Exception e) {
            throw AuthenticationErrorCode.RESOURCE_SERVER_UNAVAILABLE.toException();
        }
    }

    private String generateState() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    private MultiValueMap<String, String> createAccessTokenRequestBody(String authCode, String state) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", naverOAuthProperty.getClientId());
        parameters.add("client_secret", naverOAuthProperty.getClientSecret());
        parameters.add("grant_type", GRANT_TYPE);
        parameters.add("state", state);
        parameters.add("code", authCode);
        return parameters;
    }
}
