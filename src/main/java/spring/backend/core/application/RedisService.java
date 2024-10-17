package spring.backend.core.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.backend.core.infrastructure.redis.repository.RedisRepository;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisRepository redisRepository;

    public void save(String key, String value) {
        redisRepository.save(key, value);
    }

    public void save(String key, String value, Long expireTime, TimeUnit timeUnit) {
        redisRepository.save(key, value, expireTime, timeUnit);
    }

    public String get(String key) {
        return redisRepository.get(key);
    }
}