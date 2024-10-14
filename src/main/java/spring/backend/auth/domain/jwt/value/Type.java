package spring.backend.auth.domain.jwt.value;

import lombok.Getter;

@Getter
public enum Type {
    ACCESS("access"), REFRESH("refresh");
    private final String type;

    Type(String type) {
        this.type = type;
    }
}
