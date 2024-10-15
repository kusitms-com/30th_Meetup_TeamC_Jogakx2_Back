package spring.backend.auth.application;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.backend.auth.domain.jwt.value.Type;
import spring.backend.member.domain.entity.Member;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class AccessTokenProviderService {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    public static String accessTokenProvider(Member member) {
        Date expiryDate = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS)
        );

        return Jwts.builder()
                .subject(member.getEmail()) // 추후 변경 가능
                .claim("type", Type.ACCESS.getType())
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(SECRET_KEY)
                .compact();
    }
}
