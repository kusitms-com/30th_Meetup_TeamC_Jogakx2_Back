package spring.backend.auth.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.core.configuration.swagger.ApiErrorCode;

@Tag(name = "Auth", description = "인증/인가")
public interface LogoutSwagger {
    @Operation(
            summary = "로그아웃 API",
            description = "Access Token을 만료시켜 로그아웃합니다",
            operationId = "/v1/logout"
    )
    @ApiErrorCode({
            AuthenticationErrorCode.class
    })
    ResponseEntity<?> logout(
            @Parameter(description = "쿠키에 있는 access_token", required = false)
            String accessToken
    );
}
