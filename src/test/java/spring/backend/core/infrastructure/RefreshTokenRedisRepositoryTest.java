package spring.backend.core.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.backend.auth.infrastructure.redis.repository.RefreshTokenRedisRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class RefreshTokenRedisRepositoryTest {
    @Autowired
    private RefreshTokenRedisRepository refreshTokenRedisRepository;

    @DisplayName("인자로 받은 memberId에 해당하는 refreshToken이 Redis에 존재하지 않을 때 예외를 던진다.")
    @Test
    public void throwExceptionWhenRefreshTokenSingedByMemberIdIsNotExistsInRedis() {
        // GIVEN
        UUID memberId = UUID.randomUUID();
        // WHEN & THEN
        assertThatThrownBy(() -> refreshTokenRedisRepository.findByMemberId(memberId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("리프레시 토큰이 저장소에 존재하지 않습니다.");
    }
}
