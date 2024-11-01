package spring.backend.activity.domain.entity;

import lombok.Builder;
import lombok.Getter;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.domain.value.Type;
import spring.backend.activity.exception.ActivityErrorCode;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Builder
public class Activity {

    public static final Integer MAX_SAVED_TIME = 300;

    private Long id;

    private UUID memberId;

    private Long quickStartId;

    private Integer spareTime;

    private Type type;

    private Keyword keyword;

    private String title;

    private String content;

    private String location;

    private Boolean finished;

    private LocalDateTime finishedAt;

    private Integer savedTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean deleted;

    public void validateActivityOwner(UUID memberId) {
        if (!this.memberId.equals(memberId)) {
            throw ActivityErrorCode.MEMBER_ID_MISMATCH.toException();
        }
    }

    public boolean isFinished() {
        return finished != null && finished;
    }

    public void finish() {
        if (isFinished()) {
            throw ActivityErrorCode.ALREADY_FINISHED_ACTIVITY.toException();
        }
        finished = true;
        finishedAt = LocalDateTime.now();
        savedTime = calculateSavedTime();
    }

    private Integer calculateSavedTime() {
        long savedTime = ChronoUnit.MINUTES.between(createdAt, finishedAt);
        if (savedTime < 0 || savedTime > MAX_SAVED_TIME) {
            throw ActivityErrorCode.INVALID_ACTIVITY_DURATION.toException();
        }
        return Math.toIntExact(savedTime);
    }

    public static Activity create(UUID memberId, Long quickStartId, Integer spareTime, Type type, Keyword keyword, String title, String content, String location) {
        return Activity.builder()
                .memberId(memberId)
                .quickStartId(quickStartId)
                .spareTime(spareTime)
                .type(type)
                .keyword(keyword)
                .title(title)
                .content(content)
                .location(location)
                .finished(false)
                .build();
    }
}
