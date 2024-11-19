package spring.backend.activity.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.dto.request.ActivitiesByMemberAndKeywordInMonthRequest;
import spring.backend.activity.dto.response.ActivitiesByMemberAndKeywordInMonthResponse;
import spring.backend.activity.dto.response.ActivityWithTitleAndSavedTimeResponse;
import spring.backend.activity.infrastructure.mapper.KeywordImageMapper;
import spring.backend.activity.query.dao.ActivityDao;
import spring.backend.core.util.TimeUtil;
import spring.backend.member.domain.entity.Member;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadActivitiesByMemberAndKeywordInMonthService {
    private final ActivityDao activityDao;

    public ActivitiesByMemberAndKeywordInMonthResponse readActivitiesByMemberAndKeywordInMonth(Member member, int year, int month, Keyword.Category keywordCategory) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime firstDayOfMonth = TimeUtil.toFirstDayOfMonth(yearMonth);
        LocalDateTime endDayOfMonth = TimeUtil.toEndDayOfMonth(yearMonth);
        List<ActivityWithTitleAndSavedTimeResponse> activities = activityDao.findActivitiesByMemberAndKeywordInMonth(member.getId(), firstDayOfMonth, endDayOfMonth, keywordCategory);
        long countActivitiesByMemberAndKeywordInMonth = activityDao.countActivitiesByMemberAndKeywordInMonth(member.getId(), firstDayOfMonth, endDayOfMonth, keywordCategory);
        long totalSavedTimeByKeywordInMonth = activityDao.totalSavedTimeByKeywordInMonth(member.getId(), firstDayOfMonth, endDayOfMonth, keywordCategory);
        Keyword keyword = KeywordImageMapper.getImageByCategory(keywordCategory);
        return new ActivitiesByMemberAndKeywordInMonthResponse(
                totalSavedTimeByKeywordInMonth,
                activities,
                countActivitiesByMemberAndKeywordInMonth,
                keyword
        );
    }
}
