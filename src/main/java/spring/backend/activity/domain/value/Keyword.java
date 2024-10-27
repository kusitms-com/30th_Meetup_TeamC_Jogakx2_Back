package spring.backend.activity.domain.value;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Keyword {

    @Enumerated(EnumType.STRING)
    private Category category;

    private String image;

    @Getter
    @RequiredArgsConstructor
    public enum Category {
        SELF_DEVELOPMENT("자기개발"),
        HEALTH("건강"),
        NATURE("자연"),
        CULTURE_ART("문화/예술"),
        ENTERTAINMENT("엔터테인먼트"),
        RELAXATION("휴식"),
        SOCIAL("소셜");

        private final String description;
    }

    public static Keyword create(Category category, String image) {
        return new Keyword(category, image);
    }
}
