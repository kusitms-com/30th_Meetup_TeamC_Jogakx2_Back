package spring.backend.activity.infrastructure.mapper;

import spring.backend.activity.domain.value.Keyword;

import java.util.Map;

public class KeywordImageMapper {
    private static final Map<Keyword.Category, String> CATEGORY_IMAGE_MAP = Map.of(
            Keyword.Category.SELF_DEVELOPMENT, "images/self_development.png",
            Keyword.Category.HEALTH, "images/health.png",
            Keyword.Category.NATURE, "images/nature.png",
            Keyword.Category.CULTURE_ART, "images/culture_art.png",
            Keyword.Category.ENTERTAINMENT, "images/entertainment.png",
            Keyword.Category.RELAXATION, "images/relaxation.png",
            Keyword.Category.SOCIAL, "images/social.png"
    );

    public static Keyword getImageByCategory(Keyword.Category category) {
        return Keyword.create(category, CATEGORY_IMAGE_MAP.get(category));
    }
}
