package spring.backend.activity.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.activity.dto.request.MonthlyActivityOverviewRequest;
import spring.backend.activity.dto.response.MonthlyActivityCountByKeywordResponse;
import spring.backend.activity.dto.response.MonthlyActivityOverviewResponse;
import spring.backend.activity.dto.response.MonthlySavedTimeAndActivityCountResponse;
import spring.backend.activity.query.dao.ActivityDao;
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

    public MonthlyActivityOverviewResponse readMonthlyActivityOverview(Member member, MonthlyActivityOverviewRequest monthlyActivityOverviewRequest) {
        YearMonth yearMonth = YearMonth.of(monthlyActivityOverviewRequest.year(), monthlyActivityOverviewRequest.month());
        LocalDateTime startDayOfMonth = TimeUtil.toStartDayOfMonth(yearMonth);
        LocalDateTime endDayOfMonth = TimeUtil.toEndDayOfMonth(yearMonth);
        MonthlySavedTimeAndActivityCountResponse monthlySavedTimeAndActivityCountResponse = activityDao.findMonthlyTotalSavedTimeAndTotalCount(member.getId(), startDayOfMonth, endDayOfMonth);
        List<MonthlyActivityCountByKeywordResponse> activityByKeywordSummaryResponses = activityDao.findMonthlyActivitiesByKeywordSummary(member.getId(), startDayOfMonth, endDayOfMonth);
        return new MonthlyActivityOverviewResponse(member.getUpdatedAt().getYear(), member.getUpdatedAt().getMonth(), monthlySavedTimeAndActivityCountResponse, activityByKeywordSummaryResponses);
    }
}
