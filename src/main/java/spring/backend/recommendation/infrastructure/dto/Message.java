package spring.backend.recommendation.infrastructure.dto;


import lombok.Builder;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.domain.value.Type;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.recommendation.dto.request.AIRecommendationRequest;

import java.util.Arrays;
import java.util.stream.Collectors;

import static spring.backend.activity.domain.value.Type.OFFLINE;
import static spring.backend.activity.domain.value.Type.ONLINE_AND_OFFLINE;
import static spring.backend.recommendation.infrastructure.dto.Role.SYSTEM;
import static spring.backend.recommendation.infrastructure.dto.Role.USER;

@Builder
public record Message(String role, String content) {
    public static Message createSystem(String prompt) {
        return Message.builder()
                .role(SYSTEM.getDescription())
                .content(prompt)
                .build();
    }

    public static Message createUserMessage(AIRecommendationRequest aiRecommendationRequest) {
        return Message.builder()
                .role(USER.getDescription())
                .content(createContent(aiRecommendationRequest))
                .build();
    }

    private static String createContent(AIRecommendationRequest aiRecommendationRequest) {
        int spareTime = aiRecommendationRequest.spareTime();
        Type activityType = aiRecommendationRequest.activityType();
        String keywords = parseKeywords(aiRecommendationRequest.keywords());
        String location = aiRecommendationRequest.location();

        if (isActivityTypeOfflineOrOnlineAndOffline(activityType, location)) {
            return createContentForActivityTypeOfflineOrOnlineAndOffline(spareTime, activityType, keywords, location);
        } else {
            return createContentForActivityTypeOnline(spareTime, activityType, keywords);
        }
    }

    private static String createContentForActivityTypeOnline(int spareTime, Type activityType, String keywords) {
        return String.format("\"자투리 시간: %d분\n선호활동: %s\n활동 키워드: %s\n\n 5가지 활동 추천해줘\n\n", spareTime, activityType.getDescription(), keywords);
    }

    private static String createContentForActivityTypeOfflineOrOnlineAndOffline(int spareTime, Type activityType, String keywords, String location) {
        return String.format("자투리 시간: %d분\n선호활동: %s\n활동 키워드: %s\n위치: %s\n\n 5가지 활동 추천해줘\n\n", spareTime, activityType.getDescription(), keywords, location);
    }

    private static boolean isActivityTypeOfflineOrOnlineAndOffline(Type activityType, String location) {
        if (activityType.equals(OFFLINE) && location == null || activityType.equals(ONLINE_AND_OFFLINE) && location == null) {
            throw ActivityErrorCode.NOT_EXIST_LOCATION_WHEN_OFFLINE.toException();
        }
        return activityType.equals(OFFLINE) || activityType.equals(ONLINE_AND_OFFLINE);
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
