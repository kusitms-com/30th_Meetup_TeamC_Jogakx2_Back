package spring.backend.activity.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.activity.domain.entity.Activity;
import spring.backend.activity.domain.repository.ActivityRepository;
import spring.backend.activity.dto.response.ActivityInfo;
import spring.backend.activity.presentation.dto.response.FinishActivityResponse;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.dto.response.HomeMemberInfoResponse;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class FinishActivityService {

    private final ActivityRepository activityRepository;

    public FinishActivityResponse finishActivity(Member member, Long activityId) {
        Activity activity = activityRepository.findById(activityId);
        validateActivity(activity, member);
        activity.finish();
        activityRepository.save(activity);
        return toResponse(member, activity);
    }

    private void validateActivity(Activity activity, Member member) {
        if (activity == null) {
            log.error("[FinishActivityService.validateActivity] activity is null");
            throw ActivityErrorCode.NOT_EXIST_ACTIVITY.toException();
        }
        activity.validateActivityOwner(member.getId());
    }

    private FinishActivityResponse toResponse(Member member, Activity activity) {
        HomeMemberInfoResponse memberInfo = HomeMemberInfoResponse.from(member);
        ActivityInfo activityInfo = ActivityInfo.from(activity);
        return new FinishActivityResponse(memberInfo, activityInfo);
    }
}
