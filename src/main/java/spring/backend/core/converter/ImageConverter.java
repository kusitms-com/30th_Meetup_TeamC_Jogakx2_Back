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
    private final Map<Category, String> categoryTransparent30ImageMap = new HashMap<>();

    @PostConstruct
    private void initializeImageMap() {
        categoryImageMap.put(Category.SELF_DEVELOPMENT, imageProperty.getSelfDevelopmentImageUrl());
        categoryImageMap.put(Category.HEALTH, imageProperty.getHealthImageUrl());
        categoryImageMap.put(Category.CULTURE_ART, imageProperty.getCultureArtImageUrl());
        categoryImageMap.put(Category.ENTERTAINMENT, imageProperty.getEntertainmentImageUrl());
        categoryImageMap.put(Category.RELAXATION, imageProperty.getRelaxationImageUrl());
        categoryImageMap.put(Category.SOCIAL, imageProperty.getSocialImageUrl());
    }

    @PostConstruct
    private void initializeTransparent30ImageMap() {
        categoryTransparent30ImageMap.put(Category.SELF_DEVELOPMENT, imageProperty.getTransparent30SelfDevelopmentImageUrl());
        categoryTransparent30ImageMap.put(Category.HEALTH, imageProperty.getTransparent30HealthImageUrl());
        categoryTransparent30ImageMap.put(Category.CULTURE_ART, imageProperty.getTransparent30CultureArtImageUrl());
        categoryTransparent30ImageMap.put(Category.ENTERTAINMENT, imageProperty.getTransparent30EntertainmentImageUrl());
        categoryTransparent30ImageMap.put(Category.RELAXATION, imageProperty.getTransparent30RelaxationImageUrl());
        categoryTransparent30ImageMap.put(Category.SOCIAL, imageProperty.getTransparent30SocialImageUrl());
    }

    public String convertToImageUrl(Category category) {
        if (category == null) {
            return null;
        }
        return categoryImageMap.get(category);
    }

    public String convertToTransparent30ImageUrl(Category category) {
        if (category == null) {
            return null;
        }
        return categoryTransparent30ImageMap.get(category);
    }
}
