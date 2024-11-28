package spring.backend.activity.infrastructure.queue;

import lombok.Builder;

@Builder
public record FinishActivityMessage(
        long activityId,
        int spareTime
) {
}
