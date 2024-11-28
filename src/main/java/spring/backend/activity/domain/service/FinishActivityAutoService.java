package spring.backend.activity.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.backend.activity.domain.entity.Activity;
import spring.backend.activity.infrastructure.queue.FinishActivityMessage;
import spring.backend.activity.infrastructure.queue.FinishActivityMessageProducer;
import spring.backend.core.configuration.property.queue.FinishActivityQueueProperty;

@Service
@RequiredArgsConstructor
public class FinishActivityAutoService {

    private final FinishActivityQueueProperty finishActivityQueueProperty;

    public void finishActivityAuto(Activity activity) {
        int spareTime = activity.getSpareTime();
        FinishActivityMessageProducer finishActivityMessageProducer = new FinishActivityMessageProducer(
                finishActivityQueueProperty,
                FinishActivityMessage.builder()
                        .activityId(activity.getId())
                        .spareTime(spareTime)
                        .build()
        );
        long delayTime = (long) spareTime * 60 * 1000;
        finishActivityMessageProducer.publishMessageWithDelay(delayTime);
    }
}
