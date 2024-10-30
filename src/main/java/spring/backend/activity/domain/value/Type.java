package spring.backend.activity.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {
    ONLINE("온라인"),
    OFFLINE("오프라인"),
    ONLINE_AND_OFFLINE("온라인과 오프라인 모두");

    private final String description;
}
