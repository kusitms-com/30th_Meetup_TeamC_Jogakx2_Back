package spring.backend.recommendation.infrastructure.clova.domain.value;

import lombok.Getter;

@Getter
public enum Type {
    OFFLINE,
    ONLINE,
    OFFLINE_AND_ONLINE;
}