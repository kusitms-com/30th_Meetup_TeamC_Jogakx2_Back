package spring.backend.activity.infrastructure.mapper;

import org.springframework.stereotype.Component;
import spring.backend.activity.domain.entity.Activity;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.infrastructure.persistence.jpa.entity.ActivityJpaEntity;
import spring.backend.activity.infrastructure.persistence.jpa.value.KeywordJpaValue;

import java.util.Optional;

@Component
public class ActivityMapper {

    public Activity toDomainEntity(ActivityJpaEntity activity) {
        return Activity.builder()
                .id(activity.getId())
                .memberId(activity.getMemberId())
                .quickStartId(activity.getQuickStartId())
                .spareTime(activity.getSpareTime())
                .type(activity.getType())
                .keyword(toDomainValue(activity.getKeyword()))
                .title(activity.getTitle())
                .content(activity.getContent())
                .location(activity.getLocation())
                .finished(activity.getFinished())
                .finishedAt(activity.getFinishedAt())
                .savedTime(activity.getSavedTime())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .deleted(activity.getDeleted())
                .build();
    }

    public ActivityJpaEntity toJpaEntity(Activity activity) {
        return ActivityJpaEntity.builder()
                .id(activity.getId())
                .memberId(activity.getMemberId())
                .quickStartId(activity.getQuickStartId())
                .spareTime(activity.getSpareTime())
                .type(activity.getType())
                .keyword(toJpaValue(activity.getKeyword()))
                .title(activity.getTitle())
                .content(activity.getContent())
                .location(activity.getLocation())
                .finished(activity.getFinished())
                .finishedAt(activity.getFinishedAt())
                .savedTime(activity.getSavedTime())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .deleted(Optional.ofNullable(activity.getDeleted()).orElse(false))
                .build();
    }

    private Keyword toDomainValue(KeywordJpaValue keywordJpaValue) {
        return Keyword.create(keywordJpaValue.getCategory(), keywordJpaValue.getImage());
    }

    private KeywordJpaValue toJpaValue(Keyword keyword) {
        return KeywordJpaValue.create(keyword.getCategory(), keyword.getImage());
    }
}
