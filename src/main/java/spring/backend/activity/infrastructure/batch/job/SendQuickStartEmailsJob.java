package spring.backend.activity.infrastructure.batch.job;

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
import spring.backend.activity.infrastructure.persistence.jpa.entity.QuickStartJpaEntity;
import spring.backend.activity.infrastructure.persistence.jpa.repository.QuickStartJpaRepository;
import spring.backend.core.util.email.EmailUtil;
import spring.backend.core.util.email.dto.request.SendEmailRequest;
import spring.backend.member.infrastructure.persistence.jpa.entity.MemberJpaEntity;
import spring.backend.member.infrastructure.persistence.jpa.repository.MemberJpaRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            List<String> receivers = new ArrayList<>();
            try {
                final int TIME_INTERVAL_MINUTES = 15;
                LocalTime now = LocalTime.now();
                LocalTime lowerBound = now.plusMinutes(1).withSecond(0).withNano(0);
                LocalTime upperBound = lowerBound.plusMinutes(TIME_INTERVAL_MINUTES - 1);

                log.info("[SendQuickStartEmailsJob] Searching for QuickStarts between {} and {}", lowerBound, upperBound);

                List<QuickStartJpaEntity> quickStarts = quickStartJpaRepository.findQuickStartsWithinTimeRange(lowerBound, upperBound);

                quickStarts.stream()
                        .map(QuickStartJpaEntity::getMemberId)
                        .map(memberJpaRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(MemberJpaEntity::getEmail)
                        .forEach(receivers::add);

                if (!receivers.isEmpty()) {
                    SendEmailRequest request = SendEmailRequest.builder()
                            .title("Test Title")
                            .content("Test Content")
                            .receivers(receivers.toArray(new String[0]))
                            .build();

                    try {
                        emailUtil.send(request);
                        log.info("[SendQuickStartEmailsJob] Successfully sent email to {} receivers", receivers.size());
                    } catch (Exception e) {
                        log.error("[SendQuickStartEmailsJob] Failed to send email to {} receivers", receivers.size(), e);
                    }
                } else {
                    log.warn("[SendQuickStartEmailsJob] No valid receivers found in the time range: {} - {}", lowerBound, upperBound);
                }
            } catch (Exception e) {
                log.error("[SendQuickStartEmailsJob] Error during tasklet execution", e);
            }
            return RepeatStatus.FINISHED;
        };
    }
}
