package spring.backend.activity.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import spring.backend.activity.domain.value.Type;
import spring.backend.core.infrastructure.jpa.shared.BaseLongIdEntity;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "quick_start")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuickStartJpaEntity extends BaseLongIdEntity {

    private UUID memberId;

    private String name;

    private LocalTime startTime;

    private Integer spareTime;

    @Enumerated(EnumType.STRING)
    private Type type;
}
