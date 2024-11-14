package spring.backend.core.configuration.property.queue.shared;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseQueueProperty {

    protected String exchange;

    protected String queue;

    protected String routingKey;
}
