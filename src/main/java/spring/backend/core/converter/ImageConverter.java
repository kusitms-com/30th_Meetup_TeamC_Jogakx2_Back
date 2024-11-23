package spring.backend.core.converter;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.backend.activity.domain.value.Keyword.Category;
import spring.backend.core.configuration.property.ImageProperty;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ImageConverter {

    private final ImageProperty imageProperty;
    private final Map<Category, String> categoryImageMap = new HashMap<>();

    @PostConstruct
    private void initializeImageMap() {
        categoryImageMap.put(Category.SELF_DEVELOPMENT, imageProperty.getSelfDevelopmentImageUrl());
        categoryImageMap.put(Category.HEALTH, imageProperty.getHealthImageUrl());
        categoryImageMap.put(Category.CULTURE_ART, imageProperty.getCultureArtImageUrl());
        categoryImageMap.put(Category.ENTERTAINMENT, imageProperty.getEntertainmentImageUrl());
        categoryImageMap.put(Category.RELAXATION, imageProperty.getRelaxationImageUrl());
        categoryImageMap.put(Category.SOCIAL, imageProperty.getSocialImageUrl());
    }

    public String convertToImageUrl(Category category) {
        if (category == null) {
            return null;
        }
        return categoryImageMap.get(category);
    }
}
