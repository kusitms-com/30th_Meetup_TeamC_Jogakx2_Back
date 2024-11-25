package spring.backend.activity.infrastructure.persistence.jpa.value;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import spring.backend.activity.domain.value.Keyword.Category;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class KeywordJpaValue {

    @Enumerated(EnumType.STRING)
    private Category category;

    private String image;

    public static KeywordJpaValue create(Category category, String image) {
        return new KeywordJpaValue(category, image);
    }
}
