package spring.backend.activity.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.activity.domain.entity.Activity;
import spring.backend.activity.domain.repository.ActivityRepository;
import spring.backend.quickstart.domain.repository.QuickStartRepository;
import spring.backend.activity.domain.service.FinishActivityAutoService;
import spring.backend.activity.presentation.dto.request.QuickStartActivitySelectRequest;
import spring.backend.activity.presentation.dto.response.QuickStartActivitySelectResponse;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.quickstart.exception.QuickStartErrorCode;
import spring.backend.member.domain.entity.Member;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class QuickStartActivitySelectService {
    private final ActivityRepository activityRepository;
    private final QuickStartRepository quickStartRepository;
    private final FinishActivityAutoService finishActivityAutoService;

    public QuickStartActivitySelectResponse quickStartUserActivitySelect(Member member, Long quickStartId, QuickStartActivitySelectRequest quickStartActivitySelectRequest) {
        validateQuickStart(quickStartId);
        validateRequest(quickStartActivitySelectRequest);
        Activity activity = Activity.create(member.getId(), quickStartId, quickStartActivitySelectRequest.spareTime(), quickStartActivitySelectRequest.type(), quickStartActivitySelectRequest.keyword(), quickStartActivitySelectRequest.title(), quickStartActivitySelectRequest.content(), quickStartActivitySelectRequest.location());
        Activity savedActivity = activityRepository.save(activity);
        finishActivityAutoService.finishActivityAuto(savedActivity);
        return new QuickStartActivitySelectResponse(savedActivity.getId(), savedActivity.getTitle(), savedActivity.getKeyword());
    }

    private void validateQuickStart(Long quickStartId) {
        if (quickStartRepository.findById(quickStartId) == null) {
            log.error("[QuickStartActivitySelectRequest] Invalid quickStartId.");
            throw QuickStartErrorCode.NOT_EXIST_QUICK_START.toException();
        }
    }

    private void validateRequest(QuickStartActivitySelectRequest quickStartActivitySelectRequest) {
        if (quickStartActivitySelectRequest == null) {
            log.error("[QuickStartActivitySelectRequest] Invalid request.");
            throw ActivityErrorCode.NOT_EXIST_ACTIVITY_CONDITION.toException();
        }
    }
}
