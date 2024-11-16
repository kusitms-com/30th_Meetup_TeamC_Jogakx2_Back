package spring.backend.activity.dto.response;

import java.time.LocalDateTime;

public record UserMonthlyActivitySummary(

        LocalDateTime registrationDate,

        long totalSavedTime,

        long monthlyActivityCount
) {
}
