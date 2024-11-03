package spring.backend.activity.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.activity.domain.entity.Activity;
import spring.backend.activity.domain.repository.ActivityRepository;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.dto.request.UserActivitySelectRequest;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.member.domain.entity.Member;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class UserActivitySelectService {

    private final ActivityRepository activityRepository;

    public Long userActivitySelect(Member member, UserActivitySelectRequest userActivitySelectRequest) {
        validateRequest(userActivitySelectRequest);
        Activity activity = Activity.create(member.getId(), null, userActivitySelectRequest.spareTime(), userActivitySelectRequest.type(), userActivitySelectRequest.keyword(), userActivitySelectRequest.title(), userActivitySelectRequest.content(), userActivitySelectRequest.location());
        Activity savedActivity = activityRepository.save(activity);
        return savedActivity.getId();
    }

    private void validateRequest(UserActivitySelectRequest userActivitySelectRequest) {
        if (userActivitySelectRequest == null) {
            log.error("[UserActivitySelectRequest] Invalid request.");
            throw ActivityErrorCode.NOT_EXIST_ACTIVITY_CONDITION.toException();
        }
    }
}
