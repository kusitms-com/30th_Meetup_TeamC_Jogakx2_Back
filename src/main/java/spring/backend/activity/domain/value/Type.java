package spring.backend.activity.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {
    ONLINE("온라인"),
    OFFLINE("오프라인"),
    ONLINE_AND_OFFLINE("둘 다");

    private final String description;
}
