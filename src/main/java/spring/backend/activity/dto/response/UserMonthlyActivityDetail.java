package spring.backend.activity.dto.response;

import spring.backend.activity.domain.value.Keyword.Category;

import java.time.LocalDateTime;

public record UserMonthlyActivityDetail(

        Category category,

        String title,

        int savedTime,

        LocalDateTime activityCreatedAt
) {
}
