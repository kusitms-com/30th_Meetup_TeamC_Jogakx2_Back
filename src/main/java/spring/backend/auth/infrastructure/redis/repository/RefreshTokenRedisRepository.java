package spring.backend.auth.infrastructure.redis.repository;

import io.lettuce.core.RedisConnectionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import spring.backend.auth.domain.repository.RefreshTokenRepository;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
@Log4j2
public class RefreshTokenRedisRepository implements RefreshTokenRepository {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(UUID memberId, String refreshToken, Long expireTime, TimeUnit timeUnit) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(memberId.toString(), refreshToken, expireTime, timeUnit);
        } catch (RedisConnectionException e) {
            log.error("Redis 연결 오류 : {}", e.getMessage());
            throw GlobalErrorCode.REDIS_CONNECTION_ERROR.toException();
        }
    }

    @Override
    public String findByMemberId(UUID memberId) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String refreshToken = valueOperations.get(memberId.toString());
            if (refreshToken == null || refreshToken.isEmpty()) {
                log.error("리프레시 토큰이 저장소에 존재하지 않습니다.");
                throw AuthenticationErrorCode.NOT_EXIST_REFRESH_TOKEN.toException();
            }
            return refreshToken;
        } catch (RedisConnectionException e) {
            log.error("Redis 연결 오류 : {}", e.getMessage());
            throw GlobalErrorCode.REDIS_CONNECTION_ERROR.toException();
        }
    }

    @Override
    public void deleteByMemberId(UUID memberId) {
        try {
            redisTemplate.delete(memberId.toString());
        } catch (RedisConnectionException e) {
            log.error("Redis 연결 오류 : {}", e.getMessage());
            throw GlobalErrorCode.REDIS_CONNECTION_ERROR.toException();
        }
    }
}
