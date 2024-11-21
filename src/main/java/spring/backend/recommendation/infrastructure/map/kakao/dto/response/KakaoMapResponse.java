package spring.backend.recommendation.infrastructure.map.kakao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record KakaoMapResponse(
        List<Document> documents,
        Meta meta
) {
    public record Document(
            @JsonProperty("address_name")
            String addressName,
            @JsonProperty("category_group_code")
            String categoryGroupCode,
            @JsonProperty("category_group_name")
            String categoryGroupName,
            @JsonProperty("category_name")
            String categoryName,
            String distance,
            String id,
            String phone,
            @JsonProperty("place_name")
            String placeName,
            @JsonProperty("place_url")
            String placeUrl,
            @JsonProperty("road_address_name")
            String roadAddressName,
            String x,
            String y
    ) {
    }

    public record Meta(
            @JsonProperty("is_end")
            boolean isEnd,
            @JsonProperty("pageable_count")
            int pageableCount,
            @JsonProperty("total_count")
            int totalCount,
            SameName sameName
    ) {
        public record SameName(
                List<String> region,
                List<String> keyword,
                @JsonProperty("selected_region")
                List<String> selectedRegion
        ) {
        }
    }
}
