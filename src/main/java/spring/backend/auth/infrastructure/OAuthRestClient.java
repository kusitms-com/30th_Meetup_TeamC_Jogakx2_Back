package spring.backend.auth.infrastructure;

import spring.backend.auth.presentation.dto.response.OAuthAccessTokenResponse;
import spring.backend.auth.presentation.dto.response.OAuthResourceResponse;

import java.net.URI;

public interface OAuthRestClient {

    URI getAuthUrl();

    OAuthAccessTokenResponse getAccessToken(String authCode, String state);

    OAuthResourceResponse getResource(String oauthToken);
}
