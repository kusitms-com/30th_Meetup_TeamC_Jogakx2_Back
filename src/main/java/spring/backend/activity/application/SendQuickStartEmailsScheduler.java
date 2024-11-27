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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class SendQuickStartEmailsScheduler {

    private final QuickStartRepository quickStartRepository;

    private final MemberRepository memberRepository;

    private final EmailUtil emailUtil;

    private final TemplateEngine templateEngine;

    private static final int TIME_INTERVAL_MINUTES = 15;

    @Scheduled(cron = "0 0/15 * * * ?")
    public void sendQuickStartEmails() {
        List<Member> receivers = collectEmailReceiversWithinTimeRange();

        if (!receivers.isEmpty()) {
            sendEmails(receivers);
        } else {
            log.warn("[SendQuickStartEmailsScheduler] No valid receiver found in the time range.");
        }
    }

    private void sendEmails(List<Member> receivers) {
        List<UUID> receiverIds = receivers.stream()
                .map(Member::getId)
                .collect(Collectors.toList());

        List<QuickStart> earliestQuickStartsForMembers = collectEarliestQuickStartsForMembers(receiverIds);

        receivers.forEach(receiver -> {
            QuickStart earliestQuickStart = findEarliestQuickStartForReceiver(receiver, earliestQuickStartsForMembers);
            if (earliestQuickStart != null) {
                sendEmail(receiver, earliestQuickStart);
            } else {
                log.warn("[SendQuickStartEmailsScheduler] No quick start found for receiver {}", receiver);
            }
        });
    }

    private List<Member> collectEmailReceiversWithinTimeRange() {
        LocalTime lowerBound = getLowerBound();
        LocalTime upperBound = getUpperBound(lowerBound);

        log.info("[SendQuickStartEmailsScheduler] Searching for receivers between {} and {}", lowerBound, upperBound);

        return memberRepository.findMembersForQuickStartsInTimeRange(lowerBound, upperBound);
    }

    private List<QuickStart> collectEarliestQuickStartsForMembers(List<UUID> receiverIds) {
        LocalTime lowerBound = getLowerBound();
        LocalTime upperBound = getUpperBound(lowerBound);

        return quickStartRepository.findEarliestQuickStartsForMembers(receiverIds, lowerBound, upperBound);
    }

    private QuickStart findEarliestQuickStartForReceiver(Member receiver, List<QuickStart> earliestQuickStartsForMembers) {
        return earliestQuickStartsForMembers.stream()
                .filter(quickStart -> receiver.getId().equals(quickStart.getMemberId()))
                .findFirst()
                .orElse(null);
    }

    private void sendEmail(Member receiver, QuickStart earliestQuickStart) {
        String title = generateEmailTitle(receiver, earliestQuickStart);
        SendEmailRequest request = SendEmailRequest.builder()
                .title(title)
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

    private String generateEmailTitle(Member receiver, QuickStart earliestQuickStart) {
        return "%s 님, %s의 시간 조각을 모으러 갈 시간이에요 ⏰".formatted(receiver.getNickname(), earliestQuickStart.getName());
    }

    private String generateEmailContent() {
        Context context = new Context();
        return templateEngine.process("mail", context);
    }

    private LocalTime getLowerBound() {
        LocalTime now = LocalTime.now();
        return now.plusMinutes(1).withSecond(0).withNano(0);
    }

    private LocalTime getUpperBound(LocalTime lowerBound) {
        return lowerBound.plusMinutes(TIME_INTERVAL_MINUTES - 1);
    }
}
