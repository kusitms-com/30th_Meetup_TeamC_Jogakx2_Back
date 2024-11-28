package spring.backend.quickstart.infrastructure.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import spring.backend.quickstart.infrastructure.persistence.jpa.entity.QuickStartJpaEntity;
import spring.backend.quickstart.infrastructure.persistence.jpa.repository.QuickStartJpaRepository;
import spring.backend.core.util.email.EmailUtil;
import spring.backend.core.util.email.dto.request.SendEmailRequest;
import spring.backend.member.infrastructure.persistence.jpa.entity.MemberJpaEntity;
import spring.backend.member.infrastructure.persistence.jpa.repository.MemberJpaRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class SendQuickStartEmailsJob {

    private final JobRepository jobRepository;

    private final JobLauncher jobLauncher;

    private final PlatformTransactionManager platformTransactionManager;

    private final QuickStartJpaRepository quickStartJpaRepository;

    private final MemberJpaRepository memberJpaRepository;

    private final EmailUtil emailUtil;

    private final TemplateEngine templateEngine;

    private static final int TIME_INTERVAL_MINUTES = 15;

    //    @Scheduled(cron = "0 0/15 * * * ?")
    public void sendQuickStartEmailsJobScheduler() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("time", new Date())
                    .toJobParameters();
            jobLauncher.run(sendQuickStartEmailsJob(), jobParameters);
        }  catch (Exception e) {
            log.error("[SendQuickStartEmailsJob] Scheduler encountered an error at {}", LocalDateTime.now(), e);
        }
    }

    public Job sendQuickStartEmailsJob() {
        final String JOB_NAME = "sendQuickStartEmailsJob";
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(sendQuickStartEmailsStep())
                .build();
    }

    public Step sendQuickStartEmailsStep() {
        final String STEP_NAME = "sendQuickStartEmailsStep";
        return new StepBuilder(STEP_NAME, jobRepository)
                .tasklet(sendQuickStartEmailsTasklet(), platformTransactionManager)
                .build();
    }

    public Tasklet sendQuickStartEmailsTasklet() {
        return (contribution, chunkContext) -> {
            try {
                List<MemberJpaEntity> receivers = collectEmailReceiversWithinTimeRange();
                if (!receivers.isEmpty()) {
                    List<QuickStartJpaEntity> earliestQuickStarts = collectEarliestQuickStartsForMembers(receivers);
                    sendEmailsToReceivers(receivers, earliestQuickStarts);
                } else {
                    log.warn("[SendQuickStartEmailsJob] No valid receiver found in the time range.");
                }
            } catch (Exception e) {
                log.error("[SendQuickStartEmailsJob] Error during tasklet execution", e);
            }
            return RepeatStatus.FINISHED;
        };
    }

    private List<MemberJpaEntity> collectEmailReceiversWithinTimeRange() {
        LocalTime lowerBound = getLowerBound();
        LocalTime upperBound = getUpperBound(lowerBound);

        return memberJpaRepository.findMembersForQuickStartsInTimeRange(getLowerBound(), upperBound);
    }

    private List<QuickStartJpaEntity> collectEarliestQuickStartsForMembers(List<MemberJpaEntity> receivers) {
        LocalTime lowerBound = getLowerBound();
        LocalTime upperBound = getUpperBound(lowerBound);

        List<UUID> receiverIds = receivers.stream()
                .map(MemberJpaEntity::getId)
                .collect(Collectors.toList());
        return quickStartJpaRepository.findEarliestQuickStartsForMembers(receiverIds, lowerBound, upperBound);
    }

    private void sendEmailsToReceivers(List<MemberJpaEntity> receivers, List<QuickStartJpaEntity> earliestQuickStarts) {
        for (MemberJpaEntity receiver : receivers) {
            QuickStartJpaEntity earliestQuickStart = findEarliestQuickStartForReceiver(receiver, earliestQuickStarts);
            if (earliestQuickStart != null) {
                String title = generateEmailTitle(receiver, earliestQuickStart);
                SendEmailRequest request = SendEmailRequest.builder()
                        .title(title)
                        .content(generateEmailContent())
                        .receiver(receiver.getEmail())
                        .build();
                try {
                    emailUtil.send(request);
                    log.info("[SendQuickStartEmailsJob] Successfully sent email to {}", receiver);
                } catch (Exception e) {
                    log.error("[SendQuickStartEmailsJob] Failed to send email to {}", receiver, e);
                }
            }
        }
    }

    private QuickStartJpaEntity findEarliestQuickStartForReceiver(MemberJpaEntity receiver, List<QuickStartJpaEntity> earliestQuickStarts) {
        return earliestQuickStarts.stream()
                .filter(q -> q.getMemberId().equals(receiver.getId()))
                .findFirst()
                .orElse(null);
    }

    private String generateEmailTitle(MemberJpaEntity receiver, QuickStartJpaEntity earliestQuickStart) {
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
