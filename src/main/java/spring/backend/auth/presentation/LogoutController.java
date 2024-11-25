package spring.backend.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.auth.application.RefreshTokenService;
import spring.backend.auth.presentation.swagger.LogoutSwagger;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class LogoutController implements LogoutSwagger {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/v1/logout")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public void logout(@AuthorizedMember Member member) {
        refreshTokenService.deleteRefreshToken(member.getId());
    }
}
