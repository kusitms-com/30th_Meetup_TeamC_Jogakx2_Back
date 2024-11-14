package spring.backend.core.infrastructure.queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import spring.backend.core.configuration.ApplicationContextProvider;
import spring.backend.core.configuration.property.queue.shared.BaseQueueProperty;

@RequiredArgsConstructor
@Log4j2
public abstract class MessageProducer<T extends BaseQueueProperty, R> {

    private static final RabbitTemplate rabbitTemplate = ApplicationContextProvider.getBean("rabbitTemplate", RabbitTemplate.class);

    protected final T queueProperty;

    protected final R message;

    public void publishMessage() {
        try {
            rabbitTemplate.convertAndSend(queueProperty.getExchange(), queueProperty.getRoutingKey(), message);
        } catch (Exception e) {
            log.error("[MessagePublisher] - publishMessage Failed", e);
        }
    }

    public void publishMessageWithDelay(long delayTime) {
        try {
            rabbitTemplate.convertAndSend(
                    queueProperty.getExchange(),
                    queueProperty.getRoutingKey(),
                    message,
                    messagePostProcessor -> {
                        messagePostProcessor.getMessageProperties().setDelayLong(delayTime);
                        return messagePostProcessor;
                    }
            );
        } catch (Exception e) {
            log.error("[MessagePublisher] - publishMessageWithDelay Failed", e);
        }
    }
}
