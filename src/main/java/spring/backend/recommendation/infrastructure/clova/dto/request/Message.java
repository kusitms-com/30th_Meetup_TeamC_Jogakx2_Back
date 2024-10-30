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

    private static final String DEFAULT_SYSTEM_CONTENT = "사용자에게 자투리 시간 , 원하는 활동 타입, 활동 키워드, 위치를 입력받은 뒤 입력받은 값들을 고려해 활동을 추천해줘.\n" +
            "\n" +
            "예를 들어 입력으로 \n" +
            "자투리 시간 : 20분 , 선호활동 : ONLINE , 활동 키워드: RELAXATION,\n" +
            "\n" +
            "답변은 다음과 같은 형식으로 해줘.\n" +
            "\n" +
            "title : 마음의 편안을 가져다주는 명상 음악 20분 듣기\n" +
            "content: 휴식에는 역시 명상이 최고!\n" +
            "\n" +
            "답변 예시와 비슷한 형태로 5가지의 활동을 추천해줘.\n" +
            "\n" +
            "답변의 형식을 꼭 지켜줘\n" +
            "title : string \n" +
            "content : string";

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

        if (isActivityTypeOfflineOrOnlineAndOffline(activityType, location)) {
            return  String.format("자투리 시간: %d분\n선호활동: %s\n활동 키워드: %s\n위치: %s\n\n활동 추천해줘\n\n", spareTime, activityType.getDescription(), keywords, location);
        } else {
            return String.format("자투리 시간: %d분\n선호활동: %s\n활동 키워드: %s\n\n활동 추천해줘\n\n", spareTime, activityType.getDescription(), keywords);
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