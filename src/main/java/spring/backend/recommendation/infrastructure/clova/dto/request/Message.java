package spring.backend.recommendation.infrastructure.clova.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.domain.value.Type;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;
import spring.backend.recommendation.infrastructure.clova.exception.ClovaErrorCode;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Builder
public class Message {

    private static final String DEFAULT_SYSTEM_CONTENT = "너는 자투리시간과 활동의 타입(온라인,오프라인, 둘다)를 받고, 키워드와 위치 정보를 받아 사용자에게 5가지의 활동을 추천해주는 봇이야. 추천목록만 보내주면 되고, 각 추천목록은 줄바꿈으로 구분해줘. 추천목록을 제외한 잡설은 보내지마.";

    private String role;
    private String content;

    @RequiredArgsConstructor
    @Getter
    public enum Role {
        SYSTEM("system"), USER("user");
        private final String description;
    }

    public static Message createSystem() {
        return Message.builder()
                .role(Role.SYSTEM.getDescription())
                .content(DEFAULT_SYSTEM_CONTENT)
                .build();
    }

    public static Message createMessage(ClovaRecommendationRequest clovaRecommendationRequest) {
        return Message.builder().role(Role.USER.getDescription()).content(createContent(clovaRecommendationRequest)).build();
    }

    private static String createContent(ClovaRecommendationRequest clovaRecommendationRequest) {
        int spareTime = clovaRecommendationRequest.getSpareTime();
        Type activityType = clovaRecommendationRequest.getActivityType();
        String keywords = parseKeywords(clovaRecommendationRequest.getKeywords());
        String location = clovaRecommendationRequest.getLocation();

        if (isActivityTypeOffline(activityType, location)) {
            return String.format("자투리 시간: %d분\n선호활동: %s\n활동 키워드: %s\n위치: %s\n\n활동 추천해줘\n\n", spareTime, activityType, keywords, location);
        } else {
            return String.format("자투리 시간: %d분\n선호활동: %s\n활동 키워드: %s\n\n활동 추천해줘\n\n", spareTime, activityType, keywords);
        }
    }

    private static boolean isActivityTypeOffline(Type activityType, String location) {
        if (activityType.equals(Type.OFFLINE) && location == null) {
            throw ClovaErrorCode.NOT_EXIST_LOCATION_WHEN_OFFLINE.toException();
        }
        return activityType.equals(Type.OFFLINE);
    }

    private static String parseKeywords(Keyword.Category[] keywords) {
        if (keywords.length == 0) {
            return Arrays.stream(Keyword.Category.values())
                    .map(Keyword.Category::getDescription)
                    .collect(Collectors.joining(", "));
        } else {
            return Arrays.stream(keywords)
                    .map(Keyword.Category::getDescription)
                    .collect(Collectors.joining(", "));
        }
    }
}