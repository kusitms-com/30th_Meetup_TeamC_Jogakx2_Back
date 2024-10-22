package spring.backend.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.backend.auth.dto.response.RotateTokenResponse;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.core.application.JwtService;
import spring.backend.member.application.MemberService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RotateAccessTokenService {
    private final MemberService memberService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public RotateTokenResponse rotateAccessToken(String refreshToken) {
        UUID memberId = extractMemberIdFromRefreshToken(refreshToken);
        validateRefreshToken(memberId, refreshToken);
        return new RotateTokenResponse(jwtService.provideAccessToken(memberService.findByMemberId(memberId)));
    }

    private UUID extractMemberIdFromRefreshToken(String refreshToken) {
        if(refreshToken == null) {
            log.error("쿠키에 refreshToken이 존재하지 않습니다.");
            throw AuthenticationErrorCode.MISSING_COOKIE_VALUE.toException();
        }
        return UUID.fromString(jwtService.getPayload(refreshToken).get("memberId", String.class));
    }

    private void validateRefreshToken(UUID memberId, String refreshToken) {
        String savedRefreshToken = refreshTokenService.getRefreshToken(memberId);
        if (!savedRefreshToken.equals(refreshToken)) {
            log.error("리프레시 토큰이 저장소에 존재하지 않습니다.");
            throw AuthenticationErrorCode.NOT_EXIST_REFRESH_TOKEN.toException();
        }
    }
}
