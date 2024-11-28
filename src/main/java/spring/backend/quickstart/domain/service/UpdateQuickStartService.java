package spring.backend.quickstart.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.quickstart.domain.entity.QuickStart;
import spring.backend.quickstart.domain.repository.QuickStartRepository;
import spring.backend.quickstart.dto.request.QuickStartRequest;
import spring.backend.quickstart.exception.QuickStartErrorCode;
import spring.backend.core.util.TimeUtil;
import spring.backend.member.domain.entity.Member;

import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class UpdateQuickStartService {

    private final QuickStartRepository quickStartRepository;

    public void updateQuickStart(Member member, QuickStartRequest request, Long quickStartId) {
        QuickStart quickStart = quickStartRepository.findById(quickStartId);
        validateUpdateRequest(member, request, quickStart);

        LocalTime startTime = TimeUtil.toLocalTime(request.meridiem(), request.hour(), request.minute());
        validateStartTime(startTime);

        quickStart.update(request.name(), startTime, request.spareTime(), request.type());
        quickStartRepository.save(quickStart);
    }

    private void validateUpdateRequest(Member member, QuickStartRequest request, QuickStart quickStart) {
        validateQuickStartExistence(quickStart);
        validateRequest(request);
        validateMemberId(member.getId(), quickStart.getMemberId());
    }

    private void validateQuickStartExistence(QuickStart quickStart) {
        if (quickStart == null) {
            log.error("[validateQuickStartExistence] QuickStart does not exist.");
            throw QuickStartErrorCode.NOT_EXIST_QUICK_START.toException();
        }
    }

    private void validateRequest(QuickStartRequest request) {
        if (request == null) {
            log.error("[validateRequest] Request is null.");
            throw QuickStartErrorCode.NOT_EXIST_QUICK_START_CONDITION.toException();
        }
    }

    private void validateMemberId(UUID memberId, UUID quickStartMemberId) {
        if (!memberId.equals(quickStartMemberId)) {
            log.error("[validateMemberId] Member id mismatch");
            throw QuickStartErrorCode.MEMBER_ID_MISMATCH.toException();
        }
    }

    private void validateStartTime(LocalTime time) {
        if (time == null) {
            log.error("[UpdateQuickStartService] Invalid start time.");
            throw QuickStartErrorCode.START_TIME_CONVERSION_FAILED.toException();
        }
    }
}
