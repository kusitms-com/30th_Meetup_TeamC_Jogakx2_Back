package spring.backend.activity.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spring.backend.activity.domain.entity.QuickStart;
import spring.backend.activity.domain.repository.QuickStartRepository;
import spring.backend.core.util.email.EmailUtil;
import spring.backend.core.util.email.dto.request.SendEmailRequest;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class SendQuickStartEmailsScheduler {

    private final QuickStartRepository quickStartRepository;

    private final MemberRepository memberRepository;

    private final EmailUtil emailUtil;

    @Scheduled(cron = "0 0/15 * * * ?")
    public void sendQuickStartEmails() {
        List<String> receivers = new ArrayList<>();

        final int TIME_INTERVAL_MINUTES = 15;
        LocalTime now = LocalTime.now();
        LocalTime lowerBound = now.plusMinutes(1).withSecond(0).withNano(0);
        LocalTime upperBound = lowerBound.plusMinutes(TIME_INTERVAL_MINUTES - 1);

        log.info("[SendQuickStartEmailsScheduler] Searching for QuickStarts between {} and {}", lowerBound, upperBound);

        List<QuickStart> quickStarts = quickStartRepository.findQuickStartsWithinTimeRange(lowerBound, upperBound);

        quickStarts.stream()
                .map(QuickStart::getMemberId)
                .map(memberRepository::findById)
                .filter(Objects::nonNull)
                .map(Member::getEmail)
                .filter(Objects::nonNull)
                .forEach(receivers::add);

        if (!receivers.isEmpty()) {
            SendEmailRequest request = SendEmailRequest.builder()
                    .title("Test Title")
                    .content("Test Content")
                    .receivers(receivers.toArray(new String[0]))
                    .build();

            try {
                emailUtil.send(request);
                log.info("[SendQuickStartEmailsScheduler] Successfully sent email to {} receivers", receivers.size());
            } catch (Exception e) {
                log.error("[SendQuickStartEmailsScheduler] Failed to send email to {} receivers", receivers.size(), e);
            }
        } else {
            log.warn("[SendQuickStartEmailsScheduler] No valid receivers found in the time range: {} - {}", lowerBound, upperBound);
        }
    }
}
