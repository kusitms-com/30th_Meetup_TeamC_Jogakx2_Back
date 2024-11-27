package spring.backend.activity.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import spring.backend.activity.domain.entity.QuickStart;
import spring.backend.activity.domain.repository.QuickStartRepository;
import spring.backend.core.util.email.EmailUtil;
import spring.backend.core.util.email.dto.request.SendEmailRequest;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;

import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class SendQuickStartEmailsScheduler {

    private final QuickStartRepository quickStartRepository;

    private final MemberRepository memberRepository;

    private final EmailUtil emailUtil;

    private final TemplateEngine templateEngine;

    @Scheduled(cron = "0 0/15 * * * ?")
    public void sendQuickStartEmails() {
        Set<String> receivers = collectEmailReceiversWithinTimeRange();

        if (!receivers.isEmpty()) {
            sendEmails(receivers);
        } else {
            log.warn("[SendQuickStartEmailsScheduler] No valid receiver found in the time range.");
        }
    }

    private Set<String> collectEmailReceiversWithinTimeRange() {
        Set<String> receivers = new HashSet<>();
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

        return receivers;
    }

    private void sendEmails(Set<String> receivers) {
        for (String receiver : receivers) {
            SendEmailRequest request = SendEmailRequest.builder()
                    .title("Test Title")
                    .content(generateEmailContent())
                    .receiver(receiver)
                    .build();
            try {
                emailUtil.send(request);
                log.info("[SendQuickStartEmailsScheduler] Successfully sent email to {}", receiver);
            } catch (Exception e) {
                log.error("[SendQuickStartEmailsScheduler] Failed to send email to {}", receiver, e);
            }
        }
    }

    private String generateEmailContent() {
        Context context = new Context();
        return templateEngine.process("mail", context);
    }
}
