package spring.backend.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.auth.application.RotateAccessTokenService;
import spring.backend.auth.dto.response.RotateAccessTokenResponse;
import spring.backend.core.presentation.RestResponse;

@RestController
@RequestMapping("/v1/token/rotate")
@RequiredArgsConstructor
public class RotateAccessTokenController {
    private final RotateAccessTokenService rotateTokenService;

    @GetMapping
    public ResponseEntity<RestResponse<RotateAccessTokenResponse>> rotateAccessToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        return ResponseEntity.ok(new RestResponse<>(rotateTokenService.rotateAccessToken(refreshToken)));
    }
}
