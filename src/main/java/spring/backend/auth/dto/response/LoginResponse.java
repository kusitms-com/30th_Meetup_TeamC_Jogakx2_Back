package spring.backend.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.member.domain.value.Role;

import java.time.LocalDateTime;

public record LoginResponse(

        @Schema(description = "액세스 토큰")
        String accessToken,

        @Schema(description = "리프레시 토큰")
        String refreshToken,

        @Schema(description = "사용자 유형(MEMBER, GUEST)", example = "MEMBER")
        Role role,

        @Schema(description = "사용자 가입 날짜", example = "2024-11-14T06:10:55.091954")
        LocalDateTime registrationDate
) {
    public static LoginResponse of(String accessToken, String refreshToken, Role role, LocalDateTime registrationDate) {
        return new LoginResponse(accessToken, refreshToken, role, registrationDate);
    }
}
