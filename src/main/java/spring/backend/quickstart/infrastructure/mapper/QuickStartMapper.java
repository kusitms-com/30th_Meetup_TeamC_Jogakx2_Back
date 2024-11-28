package spring.backend.quickstart.infrastructure.mapper;

import org.springframework.stereotype.Component;
import spring.backend.quickstart.domain.entity.QuickStart;
import spring.backend.quickstart.infrastructure.persistence.jpa.entity.QuickStartJpaEntity;

import java.util.Optional;

@Component
public class QuickStartMapper {

    public QuickStart toDomainEntity(QuickStartJpaEntity quickStart) {
        return QuickStart.builder()
                .id(quickStart.getId())
                .memberId(quickStart.getMemberId())
                .name(quickStart.getName())
                .startTime(quickStart.getStartTime())
                .spareTime(quickStart.getSpareTime())
                .type(quickStart.getType())
                .createdAt(quickStart.getCreatedAt())
                .updatedAt(quickStart.getUpdatedAt())
                .deleted(quickStart.getDeleted())
                .build();
    }

    public QuickStartJpaEntity toJpaEntity(QuickStart quickStart) {
        return QuickStartJpaEntity.builder()
                .id(quickStart.getId())
                .memberId(quickStart.getMemberId())
                .name(quickStart.getName())
                .startTime(quickStart.getStartTime())
                .spareTime(quickStart.getSpareTime())
                .type(quickStart.getType())
                .createdAt(quickStart.getCreatedAt())
                .updatedAt(quickStart.getUpdatedAt())
                .deleted(Optional.ofNullable(quickStart.getDeleted()).orElse(false))
                .build();
    }
}
