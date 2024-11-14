package spring.backend.activity.infrastructure.queue;

import spring.backend.core.configuration.property.queue.FinishActivityQueueProperty;
import spring.backend.core.infrastructure.queue.MessageProducer;

public class FinishActivityMessageProducer extends MessageProducer<FinishActivityQueueProperty, FinishActivityMessage> {

    public FinishActivityMessageProducer(FinishActivityQueueProperty queueProperty, FinishActivityMessage message) {
        super(queueProperty, message);
    }
}
