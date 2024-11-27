package spring.backend.activity.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import spring.backend.core.util.email.EmailUtil;
import spring.backend.core.util.email.dto.request.SendEmailRequest;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class SendQuickStartEmailsScheduler {

    private final MemberRepository memberRepository;

    private final EmailUtil emailUtil;

    private final TemplateEngine templateEngine;

    @Scheduled(cron = "0 0/15 * * * ?")
    public void sendQuickStartEmails() {
        List<Member> receivers = collectEmailReceiversWithinTimeRange();

        if (!receivers.isEmpty()) {
            sendEmails(receivers);
        } else {
            log.warn("[SendQuickStartEmailsScheduler] No valid receiver found in the time range.");
        }
    }

    private List<Member> collectEmailReceiversWithinTimeRange() {
        final int TIME_INTERVAL_MINUTES = 15;
        LocalTime now = LocalTime.now();
        LocalTime lowerBound = now.plusMinutes(1).withSecond(0).withNano(0);
        LocalTime upperBound = lowerBound.plusMinutes(TIME_INTERVAL_MINUTES - 1);

        log.info("[SendQuickStartEmailsScheduler] Searching for receivers between {} and {}", lowerBound, upperBound);

        return memberRepository.findMembersForQuickStartsInTimeRange(lowerBound, upperBound);
    }

    private void sendEmails(List<Member> receivers) {
        for (Member receiver : receivers) {
            SendEmailRequest request = SendEmailRequest.builder()
                    .title("Test Title")
                    .content(generateEmailContent())
                    .receiver(receiver.getEmail())
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
