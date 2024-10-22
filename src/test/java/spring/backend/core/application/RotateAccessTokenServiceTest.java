package spring.backend.core.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.backend.auth.application.RotateAccessTokenService;
import spring.backend.core.exception.DomainException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class RotateAccessTokenServiceTest {
    @Autowired
    private RotateAccessTokenService rotateAccessTokenService;

    @DisplayName("Cookie에 refreshToken이 존재하지 않는 경우 예외를 발생시킨다.")
    @Test
    void throwExceptionWhenRefreshTokenNotExistsInCookie() {
        // when, then
        assertThatThrownBy(() -> rotateAccessTokenService.rotateAccessToken(null))
                .isInstanceOf(DomainException.class)
                .hasMessage("쿠키값이 존재하지 않습니다.");
    }
}
