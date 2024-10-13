package spring.backend.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.auth.infrastructure.OAuthRestClient;
import spring.backend.auth.infrastructure.OAuthRestClientFactory;
import spring.backend.member.domain.value.Provider;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthorizeOAuthService {

    private final OAuthRestClientFactory oAuthRestClientFactory;

    public URI getAuthorizeUrl(String providerName) {
        if (providerName == null || providerName.isEmpty()) {
            log.error("[AuthorizeOAuthService] Invalid provider name");
            throw AuthenticationErrorCode.NOT_EXIST_PROVIDER.toException();
        }
        Provider provider = Provider.valueOf(providerName.toUpperCase());
        OAuthRestClient oAuthRestClient = oAuthRestClientFactory.getOAuthRestClient(provider);
        return oAuthRestClient.getAuthUrl();
    }
}
