package spring.backend.core.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.backend.core.infrastructure.redis.repository.RedisRepository;

import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisRepository redisRepository;

    public void saveRefreshToken(UUID memberId, String refreshToken, Long expireTime, ChronoUnit chronoUnit) {
        TimeUnit timeUnit = convertChronoUnitToTimeUnit(chronoUnit);
        redisRepository.saveRefreshToken(memberId, refreshToken, expireTime, timeUnit);
    }

    public String getRefreshToken(UUID memberId) {
        return redisRepository.getRefreshToken(memberId);
    }

    private TimeUnit convertChronoUnitToTimeUnit(ChronoUnit chronoUnit) {
        switch (chronoUnit) {
            case NANOS:
                return TimeUnit.NANOSECONDS;
            case MICROS:
                return TimeUnit.MICROSECONDS;
            case MILLIS:
                return TimeUnit.MILLISECONDS;
            case SECONDS:
                return TimeUnit.SECONDS;
            case MINUTES:
                return TimeUnit.MINUTES;
            case HOURS:
                return TimeUnit.HOURS;
            case DAYS:
                return TimeUnit.DAYS;
            default:
                throw new UnsupportedOperationException("Unsupported ChronoUnit: " + chronoUnit);
        }
    };
}