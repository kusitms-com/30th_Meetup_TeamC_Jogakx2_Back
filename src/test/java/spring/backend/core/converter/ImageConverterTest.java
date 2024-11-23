package spring.backend.core.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.backend.activity.domain.value.Keyword.Category;
import spring.backend.core.configuration.property.ImageProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ImageConverterTest {

    @Autowired
    private ImageConverter imageConverter;

    @Autowired
    private ImageProperty imageProperty;

    @DisplayName("주어진 카테고리에 맞는 이미지 URL을 반환한다")
    @Test
    void mapCategoryToImageUrl() {
        assertEquals(imageProperty.getSelfDevelopmentImageUrl(), imageConverter.convertToImageUrl(Category.SELF_DEVELOPMENT));
        assertEquals(imageProperty.getHealthImageUrl(), imageConverter.convertToImageUrl(Category.HEALTH));
        assertEquals(imageProperty.getCultureArtImageUrl(), imageConverter.convertToImageUrl(Category.CULTURE_ART));
        assertEquals(imageProperty.getEntertainmentImageUrl(), imageConverter.convertToImageUrl(Category.ENTERTAINMENT));
        assertEquals(imageProperty.getRelaxationImageUrl(), imageConverter.convertToImageUrl(Category.RELAXATION));
        assertEquals(imageProperty.getSocialImageUrl(), imageConverter.convertToImageUrl(Category.SOCIAL));

        assertNull(imageConverter.convertToImageUrl(null));
    }
}
