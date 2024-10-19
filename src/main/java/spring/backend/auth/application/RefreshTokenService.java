package spring.backend.auth.application;

import io.lettuce.core.RedisConnectionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.backend.auth.domain.repository.RefreshTokenRepository;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.core.application.JwtService;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.member.domain.entity.Member;

import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RefreshTokenService {
    private final JwtService jwtService;
    private final long REFRESH_TOKEN_EXPIRATION;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(JwtService jwtService, @Value("${jwt.refresh-token-expiry}") long refreshTokenExpiry, RefreshTokenRepository refreshTokenRepository) {
        this.jwtService = jwtService;
        this.REFRESH_TOKEN_EXPIRATION = refreshTokenExpiry;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String saveRefreshToken(Member member) {
        try {
            refreshTokenRepository.save(member.getId(), jwtService.provideRefreshToken(member),REFRESH_TOKEN_EXPIRATION, convertChronoUnitToTimeUnit(ChronoUnit.DAYS));
            return getRefreshToken(member.getId());
        } catch (RedisConnectionException e) {
            throw GlobalErrorCode.REDIS_CONNECTION_ERROR.toException();
        } catch (Exception e) {
            throw GlobalErrorCode.INTERNAL_ERROR.toException();
        }
    }

    public String getRefreshToken(UUID memberId) {
        try {
            return refreshTokenRepository.findById(memberId);
        } catch (RedisConnectionException e) {
            throw GlobalErrorCode.REDIS_CONNECTION_ERROR.toException();
        } catch (Exception e) {
            throw GlobalErrorCode.INTERNAL_ERROR.toException();
        }
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
                throw AuthenticationErrorCode.UNSUPPORTED_REDIS_TIME_TYPE.toException();
        }
    }
}
