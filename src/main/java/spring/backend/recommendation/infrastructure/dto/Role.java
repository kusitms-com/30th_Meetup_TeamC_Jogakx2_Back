package spring.backend.recommendation.infrastructure.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    SYSTEM("system"), USER("user");
    private final String description;
}
