package spring.backend.auth.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.auth.infrastructure.google.GoogleOAuthRestClient;
import spring.backend.auth.infrastructure.kakao.KakaoOAuthRestClient;
import spring.backend.auth.infrastructure.naver.NaverOAuthRestClient;
import spring.backend.member.domain.value.Provider;

@Component
@RequiredArgsConstructor
public class OAuthRestClientFactory {

    private final GoogleOAuthRestClient googleOAuthRestClient;

    private final NaverOAuthRestClient naverOAuthRestClient;

    private final KakaoOAuthRestClient kakaoOAuthRestClient;

    public OAuthRestClient getOAuthRestClient(Provider provider) {
        switch (provider) {
            case GOOGLE -> {
                return googleOAuthRestClient;
            }
            case NAVER -> {
                return naverOAuthRestClient;
            }
            case KAKAO -> {
                return kakaoOAuthRestClient;
            }
            default -> throw AuthenticationErrorCode.INVALID_PROVIDER.toException();
        }
    }
}
