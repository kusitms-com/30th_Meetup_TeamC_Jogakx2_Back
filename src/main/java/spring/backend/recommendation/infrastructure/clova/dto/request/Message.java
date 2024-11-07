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
                .content(ClovaStudioPrompt.DEFAULT_SYSTEM_PROMPT)
                .build();
    }

    public static Message createMessage(ClovaRecommendationRequest clovaRecommendationRequest) {
        return Message.builder().role(Role.USER.getDescription()).content(createContent(clovaRecommendationRequest)).build();
    }

    private static String createContent(ClovaRecommendationRequest clovaRecommendationRequest) {
        int spareTime = clovaRecommendationRequest.spareTime();
        Type activityType = clovaRecommendationRequest.activityType();
        String keywords = parseKeywords(clovaRecommendationRequest.keywords());
        String location = clovaRecommendationRequest.location();

        if (isActivityTypeOfflineOrOnlineAndOffline(activityType, location)) {
            return String.format("자투리 시간: %d분\n선호활동: %s\n활동 키워드: %s\n위치: %s\n\n 5가지 활동 추천해줘\n\n", spareTime, activityType.getDescription(), keywords, location);
        } else {
            return String.format("자투리 시간: %d분\n선호활동: %s\n활동 키워드: %s\n\n 5가지 활동 추천해줘\n\n", spareTime, activityType.getDescription(), keywords);
        }
    }

    private static boolean isActivityTypeOfflineOrOnlineAndOffline(Type activityType, String location) {
        if (activityType.equals(Type.OFFLINE) && location == null || activityType.equals(Type.ONLINE_AND_OFFLINE) && location == null) {
            throw ClovaErrorCode.NOT_EXIST_LOCATION_WHEN_OFFLINE.toException();
        }
        return activityType.equals(Type.OFFLINE) || activityType.equals(Type.ONLINE_AND_OFFLINE);
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
