package spring.backend.activity.dto.response;

import java.util.List;

public record ActivityCalendarResponse(

        UserMonthlyActivitySummary summary,

        List<UserMonthlyActivityDetail> monthlyActivities
) {
    public static ActivityCalendarResponse of(UserMonthlyActivitySummary summary, List<UserMonthlyActivityDetail> details) {
        return new ActivityCalendarResponse(summary, details);
    }
}
