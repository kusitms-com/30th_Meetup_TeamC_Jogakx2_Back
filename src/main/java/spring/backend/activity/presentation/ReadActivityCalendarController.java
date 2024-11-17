package spring.backend.activity.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.activity.dto.request.ReadActivityCalendarRequest;
import spring.backend.activity.dto.response.ActivityCalendarResponse;
import spring.backend.activity.dto.response.UserMonthlyActivityDetail;
import spring.backend.activity.dto.response.UserMonthlyActivitySummary;
import spring.backend.activity.presentation.swagger.ReadActivityCalendarSwagger;
import spring.backend.activity.query.dao.ActivityDao;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReadActivityCalendarController implements ReadActivityCalendarSwagger {

    private final ActivityDao activityDao;

    @Authorization
    @GetMapping("/v1/activity-calendar")
    public ResponseEntity<RestResponse<ActivityCalendarResponse>> readActivityCalendar(@AuthorizedMember Member member, @Valid ReadActivityCalendarRequest request) {
        UserMonthlyActivitySummary summary = activityDao.findActivitySummaryByYearAndMonth(member.getId(), request.year(), request.month());
        List<UserMonthlyActivityDetail> details = activityDao.findActivityDetailsByYearAndMonth(member.getId(), request.year(), request.month());
        return ResponseEntity.ok(new RestResponse<>(ActivityCalendarResponse.of(summary, details)));
    }
}
