package spring.backend.auth.infrastructure.google;

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
import spring.backend.core.configuration.property.oauth.GoogleOAuthProperty;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Log4j2
public class GoogleOAuthRestClient implements OAuthRestClient {

    private static final String GRANT_TYPE = "authorization_code";

    private final GoogleOAuthProperty googleOAuthProperty;

    @Override
    public URI getAuthUrl() {
        return UriComponentsBuilder.fromUriString(googleOAuthProperty.getAuthUri())
                .queryParam("client_id", googleOAuthProperty.getClientId())
                .queryParam("redirect_uri", googleOAuthProperty.getRedirectUri())
                .queryParam("response_type", "code")
                .queryParam("scope", String.join(" ", googleOAuthProperty.getScope()))
                .build()
                .toUri();
    }

    @Override
    public OAuthAccessTokenResponse getAccessToken(String authCode, String state) {
        if (authCode == null || authCode.isEmpty()) {
            log.error("[GoogleOAuthRestClient] authCode is null");
            throw AuthenticationErrorCode.NOT_EXIST_AUTH_CODE.toException();
        }
        try {
            return RestClient.create()
                    .post()
                    .uri(googleOAuthProperty.getTokenUri())
                    .headers(header -> header.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .body(createAccessTokenRequestBody(authCode))
                    .retrieve()
                    .body(OAuthAccessTokenResponse.class);
        } catch (Exception e) {
            log.error("[GoogleOAuthRestClient] error", e);
            throw AuthenticationErrorCode.ACCESS_TOKEN_NOT_ISSUED.toException();
        }
    }

    @Override
    public OAuthResourceResponse getResource(String oauthToken) {
        if (oauthToken == null || oauthToken.isEmpty()) {
            log.error("[GoogleOAuthRestClient] oauthToken is null");
            throw AuthenticationErrorCode.NOT_EXIST_AUTH_CODE.toException();
        }
        try {
            return RestClient.create()
                    .get()
                    .uri(googleOAuthProperty.getResourceUri())
                    .headers(header -> {
                        header.setBearerAuth(oauthToken);
                        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    })
                    .retrieve()
                    .body(OAuthResourceResponse.class);
        } catch (Exception e) {
            throw AuthenticationErrorCode.RESOURCE_SERVER_UNAVAILABLE.toException();
        }
    }

    private MultiValueMap<String, String> createAccessTokenRequestBody(String authCode) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", googleOAuthProperty.getClientId());
        parameters.add("client_secret", googleOAuthProperty.getClientSecret());
        parameters.add("code", authCode);
        parameters.add("grant_type", GRANT_TYPE);
        parameters.add("redirect_uri", googleOAuthProperty.getRedirectUri());
        return parameters;
    }
}
