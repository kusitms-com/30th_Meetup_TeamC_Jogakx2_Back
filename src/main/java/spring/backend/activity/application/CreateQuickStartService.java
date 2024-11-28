package spring.backend.activity.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.activity.domain.entity.QuickStart;
import spring.backend.activity.domain.repository.QuickStartRepository;
import spring.backend.activity.presentation.dto.request.QuickStartRequest;
import spring.backend.activity.exception.QuickStartErrorCode;
import spring.backend.core.util.TimeUtil;
import spring.backend.member.domain.entity.Member;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CreateQuickStartService {

    private final QuickStartRepository quickStartRepository;

    public Long createQuickStart(Member member, QuickStartRequest request) {
        validateRequest(request);

        LocalTime startTime = TimeUtil.toLocalTime(request.meridiem(), request.hour(), request.minute());
        validateStartTime(startTime);

        QuickStart quickStart = QuickStart.create(member.getId(), request.name(), startTime, request.spareTime(), request.type());
        QuickStart savedQuickStart = quickStartRepository.save(quickStart);
        return savedQuickStart.getId();
    }

    private void validateRequest(QuickStartRequest request) {
        if (request == null) {
            log.error("[CreateQuickStartService] Invalid request.");
            throw QuickStartErrorCode.NOT_EXIST_QUICK_START_CONDITION.toException();
        }
    }

    private void validateStartTime(LocalTime time) {
        if (time == null) {
            log.error("[CreateQuickStartService] Invalid start time.");
            throw QuickStartErrorCode.START_TIME_CONVERSION_FAILED.toException();
        }
    }
}
