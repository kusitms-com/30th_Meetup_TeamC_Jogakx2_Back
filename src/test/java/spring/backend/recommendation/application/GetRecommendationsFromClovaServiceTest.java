package spring.backend.recommendation.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.domain.value.Type;
import spring.backend.core.exception.DomainException;
import spring.backend.recommendation.dto.request.AIRecommendationRequest;
import spring.backend.recommendation.infrastructure.clova.exception.ClovaErrorCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GetRecommendationsFromClovaServiceTest {

    @Autowired
    GetRecommendationsFromClovaService getRecommendationsFromClovaService;

    @Test
    @DisplayName("타입이 ONLINE인데 Keywords에 NATURE가 있는 경우 예외를 반환한다.")
    void throwExceptionIfOnlineActivityContainsNatureKeyword() {
        // GIVEN
        AIRecommendationRequest request = new AIRecommendationRequest(
                300,
                Type.ONLINE,
                new Keyword.Category[]{Keyword.Category.NATURE, Keyword.Category.SOCIAL},
                null
        );
        // WHEN
        DomainException ex = assertThrows(DomainException.class, () -> getRecommendationsFromClovaService.getRecommendationsFromClova(request));

        // THEN
        assertEquals(ClovaErrorCode.ONLINE_TYPE_CONTAIN_NATURE.name(), ex.getCode());
    }

    @Test
    @DisplayName("타입이 OFFLINE인데 Keywords에 SOCIAL가 있는 경우 예외를 반환한다.")
    void throwExceptionIfOfflineActivityContainsSocialKeyword() {
        // GIVEN
        AIRecommendationRequest request = new AIRecommendationRequest(
                300,
                Type.OFFLINE,
                new Keyword.Category[]{Keyword.Category.NATURE, Keyword.Category.SOCIAL},
                "서울시 강남구"
        );
        // WHEN
        DomainException ex = assertThrows(DomainException.class, () -> getRecommendationsFromClovaService.getRecommendationsFromClova(request));

        // THEN
        assertEquals(ClovaErrorCode.OFFLINE_TYPE_CONTAIN_SOCIAL.name(), ex.getCode());
    }
}
