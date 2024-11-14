package spring.backend.activity.infrastructure.queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.activity.infrastructure.persistence.jpa.entity.ActivityJpaEntity;
import spring.backend.activity.infrastructure.persistence.jpa.repository.ActivityJpaRepository;
import spring.backend.core.infrastructure.queue.MessageConsumer;

@Component
@RequiredArgsConstructor
@Transactional
@Log4j2
public class FinishActivityMessageConsumer implements MessageConsumer<FinishActivityMessage> {

    private final ActivityJpaRepository activityJpaRepository;

    @Override
    @RabbitListener(queues = "${finish-activity-queue.queue}")
    public void consumeMessage(FinishActivityMessage message) {
        try {
            if (message == null) {
                log.error("[FinishActivityMessageConsumer] Message is null");
                return;
            }
            ActivityJpaEntity activity = activityJpaRepository.findById(message.activityId()).orElseThrow(ActivityErrorCode.NOT_EXIST_ACTIVITY::toException);
            activity.finish();
        } catch (Exception e) {
            log.error("[FinishActivityMessageConsumer] Error processing message", e);
        }
    }
}
