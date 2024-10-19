package spring.backend.core.application;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import spring.backend.auth.application.RefreshTokenService;
import spring.backend.member.domain.entity.Member;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RefreshTokenServiceTest {
    @Autowired
    private RefreshTokenService refreshTokenService;

    private final UUID memberId = UUID.randomUUID();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    public void checkRedisConnection() {
        try {
            redisTemplate.getConnectionFactory().getConnection();
            System.out.println("Redis 연결 성공");
        } catch (RedisConnectionFailureException e) {
            System.err.println("Redis 연결 실패: " + e.getMessage());
        }
    }


    @AfterAll
    static void afterAll(@Qualifier("redisConnectionFactory") LettuceConnectionFactory connectionFactory) {
        connectionFactory.getConnection().flushDb();
    }

    @DisplayName("RefreshToken이 발급될 때 ID와 RefreshToken를 Redis에 저장된다")
    @Test
    void saveRefreshTokenWhenTokenReleased() {
        // given
        Member member = Member.builder()
                .id(memberId)
                .email("test@test.com")
                .build();
        // when & then
        assertThat(refreshTokenService.saveRefreshToken(member)).isEqualTo(refreshTokenService.getRefreshToken(memberId));
    }
}