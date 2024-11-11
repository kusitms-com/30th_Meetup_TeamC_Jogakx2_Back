package spring.backend.recommendation.infrastructure.map.naver.dto.response;

import java.util.List;

public record NaverMapResponse(
        String lastBuildDate,
        int total,
        int start,
        int display,
        List<NaverMapSearchItem> items
) {
    public record NaverMapSearchItem(
            String title,
            String link,
            String category,
            String description,
            String telephone,
            String address,
            String roadAddress,
            double mapx,
            double mapy
    ) {
    }
}
