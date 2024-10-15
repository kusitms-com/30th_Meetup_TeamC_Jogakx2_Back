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
public class RefreshTokenProviderService {

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    public static String refreshTokenProvider(Member member) {
        Date exiaryDate = Date.from(
                Instant.now().plus(14, ChronoUnit.DAYS)
        );

        System.out.println("secret key : " + SECRET_KEY);

        return Jwts.builder()
                .subject(member.getEmail()) // 추후 변경 가능
                .claim("type", Type.REFRESH.getType())
                .issuedAt(new Date())
                .expiration(exiaryDate)
                .signWith(SECRET_KEY)
                .compact();
    }
}