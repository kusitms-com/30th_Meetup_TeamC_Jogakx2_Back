package spring.backend.activity.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.activity.domain.entity.QuickStart;
import spring.backend.activity.domain.repository.QuickStartRepository;
import spring.backend.activity.dto.request.QuickStartRequest;
import spring.backend.activity.exception.QuickStartErrorCode;
import spring.backend.member.domain.entity.Member;

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
        quickStart.update(request.name(), request.startTime(), request.spareTime(), request.type());
        quickStartRepository.save(quickStart);
    }

    private void validateUpdateRequest(Member member, QuickStartRequest request, QuickStart quickStart) {
        validateQuickStartExistence(quickStart);
        validateRequest(request);
        validateMember(member);
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

    private void validateMember(Member member) {
        if (!member.isMember()) {
            log.error("[validateMember] Unauthorized member.");
            throw QuickStartErrorCode.NOT_A_MEMBER.toException();
        }
    }

    private void validateMemberId(UUID memberId, UUID quickStartMemberId) {
        if (!memberId.equals(quickStartMemberId)) {
            log.error("[validateMemberId] Member id mismatch");
            throw QuickStartErrorCode.MEMBER_ID_MISMATCH.toException();
        }
    }
}
