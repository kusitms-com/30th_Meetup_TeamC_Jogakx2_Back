package spring.backend.recommendation.infrastructure.clova.dto.request;


import lombok.Builder;
import lombok.Getter;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;
import spring.backend.recommendation.infrastructure.clova.exception.ClovaErrorCode;

@Getter
@Builder
public class Message {

    private ROLE role;
    private String content;

    public enum ROLE {
        system, user
    }

    public static Message createSystem() {
        String content = "너는 자투리시간과 활동의 타입(온라인,오프라인, 둘다)를 받고, 키워드와 위치 정보를 받아 사용자에게 5가지의 활동을 추천해주는 봇이야. 추천목록만 보내주면 되고, 각 추천목록은 줄바꿈으로 구분해줘. 추천목록을 제외한 잡설은 보내지마.";
        return Message.builder()
                .role(ROLE.system)
                .content(content)
                .build();
    }

    public static Message createMessage(ClovaRecommendationRequest clovaRecommendationRequest) {
        return Message.builder().role(ROLE.user).content(createContent(clovaRecommendationRequest)).build();
    }

    private static String createContent(ClovaRecommendationRequest clovaRecommendationRequest) {
        int spareTime = clovaRecommendationRequest.getSpareTime();
        String activityType = clovaRecommendationRequest.getActivityType().toString();
        Keyword.Category keyword = clovaRecommendationRequest.getKeyword();
        String location = clovaRecommendationRequest.getLocation();

        if (activityType.equals("OFFLINE")) {
            if (location == null) {
                throw ClovaErrorCode.NOT_EXIST_LOCATION_WHEN_OFFLINE.toException();
            }
            return "자투리 시간: " + spareTime + "분\\n선호활동: " + activityType + "\\n활동 키워드: " + keyword + "\\n위치 : " + location + "\\n \\n활동 추천해줘\\n\\n";
        } else {
            return "자투리 시간: " + spareTime + "분\\n선호활동: " + activityType + "\\n활동 키워드: " + keyword + "\\n \\n활동 추천해줘\\n\\n";
        }
    }
}