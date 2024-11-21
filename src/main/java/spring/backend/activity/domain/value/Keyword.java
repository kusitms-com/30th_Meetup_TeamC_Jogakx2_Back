package spring.backend.activity.domain.value;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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

        private static final Map<String, Category> DESCRIPTION_TO_CATEGORY_MAP = Arrays.stream(Category.values())
                .collect(Collectors.toMap(Category::getDescription, category -> category));

        public static Category from(String description) {
            return DESCRIPTION_TO_CATEGORY_MAP.get(description);
        }
    }

    public static Keyword create(Category category, String image) {
        return new Keyword(category, image);
    }

    public static Keyword getKeywordByCategory(Category category) {
        return Keyword.create(category, getCategoryImageMap().get(category));
    }

    private static Map<Category, String> getCategoryImageMap() {
        return Map.of(
                Keyword.Category.SELF_DEVELOPMENT, "images/self_development.png",
                Keyword.Category.HEALTH, "images/health.png",
                Keyword.Category.NATURE, "images/nature.png",
                Keyword.Category.CULTURE_ART, "images/culture_art.png",
                Keyword.Category.ENTERTAINMENT, "images/entertainment.png",
                Keyword.Category.RELAXATION, "images/relaxation.png",
                Keyword.Category.SOCIAL, "images/social.png"
        );
    }
}
