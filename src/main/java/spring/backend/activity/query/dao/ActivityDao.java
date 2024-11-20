package spring.backend.activity.query.dao;

import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.dto.response.*;
import spring.backend.activity.dto.response.HomeActivityInfoResponse;
import spring.backend.activity.dto.response.UserMonthlyActivityDetail;
import spring.backend.activity.dto.response.UserMonthlyActivitySummary;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ActivityDao {

    List<HomeActivityInfoResponse> findTodayActivities(UUID memberId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    UserMonthlyActivitySummary findActivitySummaryByYearAndMonth(UUID memberId, int year, int month);

    List<UserMonthlyActivityDetail> findActivityDetailsByYearAndMonth(UUID memberId, int year, int month);

    MonthlySavedTimeAndActivityCountResponse findMonthlyTotalSavedTimeAndTotalCount(UUID memberId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<MonthlyActivityCountByKeywordResponse> findMonthlyActivitiesByKeywordSummary(UUID memberId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<ActivityWithTitleAndSavedTimeResponse> findActivitiesByMemberAndKeywordInMonth(UUID memberId, LocalDateTime startDateTime, LocalDateTime endDateTime, Keyword.Category keywordCategory);

    TotalSavedTimeAndActivityCountByKeywordInMonth findTotalSavedTimeAndActivityCountByKeywordInMonth(UUID memberId, LocalDateTime startDateTime, LocalDateTime endDateTime, Keyword.Category keywordCategory);
}
