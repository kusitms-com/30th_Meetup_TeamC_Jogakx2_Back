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

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadActivitiesByMemberAndKeywordInMonthService {
    private final ActivityDao activityDao;

    public ActivitiesByMemberAndKeywordInMonthResponse readActivitiesByMemberAndKeywordInMonth(Member member,int year, int month, Keyword.Category keywordCategory) {
        YearMonth yearMonth = YearMonth.of(year, month);
        List<ActivityWithTitleAndSavedTimeResponse> activities = activityDao.findActivitiesByMemberAndKeywordInMonth(member.getId(), TimeUtil.toFirstDayOfMonth(yearMonth), TimeUtil.toEndDayOfMonth(yearMonth), keywordCategory);
        long countActivitiesByMemberAndKeywordInMonth = activityDao.countActivitiesByMemberAndKeywordInMonth(member.getId(), TimeUtil.toFirstDayOfMonth(yearMonth), TimeUtil.toEndDayOfMonth(yearMonth), keywordCategory);
        Long totalSavedTimeByKeywordInMonth = activityDao.totalSavedTimeByKeywordInMonth(member.getId(), TimeUtil.toFirstDayOfMonth(yearMonth), TimeUtil.toEndDayOfMonth(yearMonth), keywordCategory);
        Keyword keyword = KeywordImageMapper.getImageByCategory(keywordCategory);
        return new ActivitiesByMemberAndKeywordInMonthResponse(
                totalSavedTimeByKeywordInMonth,
                activities,
                countActivitiesByMemberAndKeywordInMonth,
                keyword
        );
    }
}
