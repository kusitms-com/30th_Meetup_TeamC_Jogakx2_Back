package spring.backend.activity.infrastructure.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import spring.backend.activity.domain.entity.Activity;
import spring.backend.activity.domain.repository.ActivityRepository;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.activity.infrastructure.mapper.ActivityMapper;
import spring.backend.activity.infrastructure.persistence.jpa.entity.ActivityJpaEntity;
import spring.backend.activity.infrastructure.persistence.jpa.repository.ActivityJpaRepository;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ActivityRepositoryImpl implements ActivityRepository {

    private final ActivityMapper activityMapper;
    private final ActivityJpaRepository activityJpaRepository;

    @Override
    public Activity findById(Long id) {
        ActivityJpaEntity activityJpaEntity = activityJpaRepository.findById(id).orElse(null);
        if (activityJpaEntity == null) {
            return null;
        }
        return activityMapper.toDomainEntity(activityJpaEntity);
    }

    @Override
    public Activity save(Activity activity) {
        try {
            ActivityJpaEntity activityJpaEntity = activityMapper.toJpaEntity(activity);
            activityJpaRepository.save(activityJpaEntity);
            return activityMapper.toDomainEntity(activityJpaEntity);
        } catch (Exception e) {
            log.error("[ActivityRepositoryImpl] Failed to save activity", e);
            throw ActivityErrorCode.ACTIVITY_SAVE_FAILED.toException();
        }
    }
}
