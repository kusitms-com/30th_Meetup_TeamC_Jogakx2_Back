package spring.backend.activity.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.domain.value.Type;
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
    private Keyword keyword;

    private String title;

    private String content;

    private String location;

    private Boolean finished;

    private LocalDateTime finishedAt;

    private Integer savedTime;
}