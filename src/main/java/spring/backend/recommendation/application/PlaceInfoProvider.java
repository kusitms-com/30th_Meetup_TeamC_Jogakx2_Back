package spring.backend.recommendation.application;

import spring.backend.recommendation.infrastructure.map.naver.dto.response.NaverMapResponse;

public interface PlaceInfoProvider {
    NaverMapResponse search(String query);
}
