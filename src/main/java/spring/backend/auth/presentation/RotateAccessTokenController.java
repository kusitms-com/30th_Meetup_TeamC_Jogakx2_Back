package spring.backend.auth.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.auth.application.RotateAccessTokenService;
import spring.backend.auth.dto.response.RotateAccessTokenResponse;
import spring.backend.core.presentation.RestResponse;

@RestController
@RequestMapping("/v1/token/rotate")
@RequiredArgsConstructor
@Log4j2
public class RotateAccessTokenController {
    private final RotateAccessTokenService rotateTokenService;

    @PostMapping
    public ResponseEntity<RestResponse<RotateAccessTokenResponse>> rotateAccessToken(
            @CookieValue(name = "access_token", required = false) String accessToken
    ) {
        RotateAccessTokenResponse rotateAccessTokenResponse = rotateTokenService.rotateAccessToken(accessToken);
        ResponseCookie cookie = ResponseCookie.from("access_token", rotateAccessTokenResponse.accessToken())
                .httpOnly(true)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
