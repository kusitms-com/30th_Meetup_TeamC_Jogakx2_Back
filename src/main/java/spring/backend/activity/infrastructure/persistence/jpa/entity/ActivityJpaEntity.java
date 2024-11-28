package spring.backend.activity.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import spring.backend.activity.infrastructure.persistence.jpa.value.KeywordJpaValue;
import spring.backend.activity.domain.value.Type;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.core.infrastructure.jpa.shared.BaseLongIdEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "activity")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityJpaEntity extends BaseLongIdEntity {

    private UUID memberId;

    private Long quickStartId;

    private Integer spareTime;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Embedded
    private KeywordJpaValue keyword;

    private String title;

    private String content;

    private String location;

    private Boolean finished;

    private LocalDateTime finishedAt;

    private Integer savedTime;

    public boolean isFinished() {
        return finished != null && finished;
    }

    public void finish() {
        if (isFinished()) {
            throw ActivityErrorCode.ALREADY_FINISHED_ACTIVITY.toException();
        }
        finished = true;
        finishedAt = LocalDateTime.now();
        savedTime = spareTime;
    }
}
