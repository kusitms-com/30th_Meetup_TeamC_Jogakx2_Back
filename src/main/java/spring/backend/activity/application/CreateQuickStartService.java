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

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CreateQuickStartService {

    private final QuickStartRepository quickStartRepository;

    public Long createQuickStart(Member member, QuickStartRequest request) {
        validateRequest(request);
        QuickStart quickStart = QuickStart.create(member.getId(), request.name(), request.startTime(), request.spareTime(), request.type());
        QuickStart savedQuickStart = quickStartRepository.save(quickStart);
        return savedQuickStart.getId();
    }

    private void validateRequest(QuickStartRequest request) {
        if (request == null) {
            log.error("[CreateQuickStartService] Invalid request.");
            throw QuickStartErrorCode.NOT_EXIST_QUICK_START_CONDITION.toException();
        }
    }
}
