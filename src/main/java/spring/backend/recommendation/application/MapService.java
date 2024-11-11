package spring.backend.recommendation.application;

import spring.backend.recommendation.infrastructure.map.naver.dto.response.NaverMapResponse;

public interface MapService {
    NaverMapResponse search(String query);
}
