package spring.backend.activity.domain.value;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Keyword {

    private final Category category;

    private final String image;

    @Getter
    @RequiredArgsConstructor
    public enum Category {
        SELF_DEVELOPMENT("자기개발"),
        HEALTH("건강"),
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
}
