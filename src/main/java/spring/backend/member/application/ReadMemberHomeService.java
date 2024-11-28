package spring.backend.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.activity.dto.response.HomeActivityInfoResponse;
import spring.backend.activity.dto.response.QuickStartResponse;
import spring.backend.activity.query.dao.ActivityDao;
import spring.backend.activity.query.dao.QuickStartDao;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.presentation.dto.response.HomeMainResponse;
import spring.backend.member.dto.response.HomeMemberInfoResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class ReadMemberHomeService {

    private final ActivityDao activityDao;

    private final QuickStartDao quickStartDao;

    public HomeMainResponse readMemberHome(Member member) {
        HomeMemberInfoResponse memberInfo = HomeMemberInfoResponse.from(member);

        List<QuickStartResponse> upcomingQuickStarts = quickStartDao.findUpcomingQuickStarts(member.getId());
        QuickStartResponse upcomingQuickStart = upcomingQuickStarts.stream().findFirst().orElse(null);

        LocalDateTime currentDateTime = LocalDateTime.now();
        List<HomeActivityInfoResponse> activities = activityDao.findTodayActivities(member.getId(), currentDateTime.toLocalDate().atStartOfDay(), currentDateTime);
        int totalSavedTime = calculateTotalSavedTime(activities);

        return HomeMainResponse.of(memberInfo, upcomingQuickStart, totalSavedTime, activities);
    }

    private int calculateTotalSavedTime(List<HomeActivityInfoResponse> activities) {
        if (activities == null || activities.isEmpty()) {
            log.info("[ReadMemberHomeService] activities is empty");
            return 0;
        }
        return activities.stream()
                .mapToInt(activity -> {
                    if (activity == null) {
                        log.info("[ReadMemberHomeService] activity is null");
                        return 0;
                    }
                    return activity.savedTime();
                })
                .sum();
    }
}
