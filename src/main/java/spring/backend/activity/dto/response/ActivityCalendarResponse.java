package spring.backend.activity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ActivityCalendarResponse(

        @Schema(description = "사용자의 월별 활동 요약",
                example = "{ \"registrationDate\": \"2024-11-14T06:10:55.091954\", \"totalSavedTime\": 120, \"monthlyActivityCount\": 10 }")
        UserMonthlyActivitySummary summary,

        @Schema(description = "사용자의 월별 활동 상세 정보 리스트",
                example = "{ \"category\": \"SELF_DEVELOPMENT\", \"title\": \"마음의 편안을 가져다주는 명상음악 20분 듣기\", \"savedTime\": 20, \"activityCreatedAt\": \"2024-11-16T14:24:08.548712\" }")
        List<UserMonthlyActivityDetail> monthlyActivities
) {
    public static ActivityCalendarResponse of(UserMonthlyActivitySummary summary, List<UserMonthlyActivityDetail> details) {
        return new ActivityCalendarResponse(summary, details);
    }
}
