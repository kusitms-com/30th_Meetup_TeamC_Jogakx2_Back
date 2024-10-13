package spring.backend.auth.dto.response;

public record LoginResponse(String accessToken, String refreshToken) {
}
