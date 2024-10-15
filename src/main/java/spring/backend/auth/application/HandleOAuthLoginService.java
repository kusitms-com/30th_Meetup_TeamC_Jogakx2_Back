package spring.backend.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.backend.auth.dto.response.LoginResponse;
import spring.backend.auth.dto.response.OAuthAccessTokenResponse;
import spring.backend.auth.dto.response.OAuthResourceResponse;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.auth.infrastructure.OAuthRestClient;
import spring.backend.auth.infrastructure.OAuthRestClientFactory;
import spring.backend.member.application.CreateMemberWithOAuthService;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.value.Provider;
import spring.backend.member.domain.value.Role;
import spring.backend.member.dto.request.CreateMemberWithOAuthRequest;

@Service
@RequiredArgsConstructor
@Log4j2
public class HandleOAuthLoginService {

    private final OAuthRestClientFactory oAuthRestClientFactory;

    private final CreateMemberWithOAuthService createMemberWithOAuthService;

    public LoginResponse handleOAuthLogin(String providerName, String code, String state) {
        if (providerName == null || providerName.isEmpty()) {
            throw AuthenticationErrorCode.NOT_EXIST_PROVIDER.toException();
        }
        Provider provider = Provider.valueOf(providerName.toUpperCase());
        OAuthRestClient oAuthRestClient = oAuthRestClientFactory.getOAuthRestClient(provider);

        OAuthAccessTokenResponse oAuthAccessTokenResponse = oAuthRestClient.getAccessToken(code, state);
        if (oAuthAccessTokenResponse == null) {
            log.error("[HandleOAuthLoginService] OAuth access token could not be retrieved.");
            throw AuthenticationErrorCode.ACCESS_TOKEN_NOT_ISSUED.toException();
        }
        OAuthResourceResponse oAuthResourceResponse = oAuthRestClient.getResource(oAuthAccessTokenResponse.getAccessToken());
        if (oAuthResourceResponse == null) {
            log.error("[HandleOAuthLoginService] OAuth resource could not be retrieved.");
            throw AuthenticationErrorCode.RESOURCE_SERVER_UNAVAILABLE.toException();
        }

        CreateMemberWithOAuthRequest createMemberWithOAuthRequest = CreateMemberWithOAuthRequest.builder().provider(provider).email(oAuthResourceResponse.getEmail()).nickname(oAuthResourceResponse.getName()).build();

        Member member = createMemberWithOAuthService.createMemberWithOAuth(createMemberWithOAuthRequest);

        return new LoginResponse(AccessTokenProviderService.accessTokenProvider(member), RefreshTokenProviderService.refreshTokenProvider(member), member.getRole());
    }
}
