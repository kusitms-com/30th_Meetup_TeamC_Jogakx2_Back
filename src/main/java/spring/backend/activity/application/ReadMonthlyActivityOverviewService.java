package spring.backend.activity.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.activity.domain.value.Keyword.Category;
import spring.backend.activity.dto.request.MonthlyActivityOverviewRequest;
import spring.backend.activity.dto.response.MonthlyActivityCountByKeywordResponse;
import spring.backend.activity.dto.response.MonthlyActivityOverviewResponse;
import spring.backend.activity.dto.response.MonthlySavedTimeAndActivityCountResponse;
import spring.backend.activity.infrastructure.persistence.jpa.value.KeywordJpaValue;
import spring.backend.activity.query.dao.ActivityDao;
import spring.backend.core.converter.ImageConverter;
import spring.backend.core.util.TimeUtil;
import spring.backend.member.domain.entity.Member;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class ReadMonthlyActivityOverviewService {

    private final ActivityDao activityDao;

    private final ImageConverter imageConverter;

    public MonthlyActivityOverviewResponse readMonthlyActivityOverview(Member member, MonthlyActivityOverviewRequest monthlyActivityOverviewRequest) {
        YearMonth yearMonth = YearMonth.of(monthlyActivityOverviewRequest.year(), monthlyActivityOverviewRequest.month());
        LocalDateTime startDayOfMonth = TimeUtil.toStartDayOfMonth(yearMonth);
        LocalDateTime endDayOfMonth = TimeUtil.toEndDayOfMonth(yearMonth);
        MonthlySavedTimeAndActivityCountResponse monthlySavedTimeAndActivityCountResponse = activityDao.findMonthlyTotalSavedTimeAndTotalCount(member.getId(), startDayOfMonth, endDayOfMonth);
        List<MonthlyActivityCountByKeywordResponse> activityByKeywordSummaryResponses = activityDao.findMonthlyActivitiesByKeywordSummary(member.getId(), startDayOfMonth, endDayOfMonth);
        List<MonthlyActivityCountByKeywordResponse> updatedActivityByKeywordSummaryResponses = activityByKeywordSummaryResponses.stream()
                .map(response -> {
                    Category category = response.keyword().getCategory();
                    String imageUrl = imageConverter.convertToTransparent30ImageUrl(category);
                    KeywordJpaValue updatedKeyword = KeywordJpaValue.create(category, imageUrl);
                    return new MonthlyActivityCountByKeywordResponse(updatedKeyword, response.activityCount());
                })
                .toList();
        return new MonthlyActivityOverviewResponse(member.getUpdatedAt().getYear(), member.getUpdatedAt().getMonth(), monthlySavedTimeAndActivityCountResponse, updatedActivityByKeywordSummaryResponses);
    }
}
