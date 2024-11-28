package spring.backend.core.configuration.property.queue;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import spring.backend.core.configuration.property.queue.shared.BaseQueueProperty;

@Component
@ConfigurationProperties("finish-activity-queue")
public class FinishActivityQueueProperty extends BaseQueueProperty {
}
