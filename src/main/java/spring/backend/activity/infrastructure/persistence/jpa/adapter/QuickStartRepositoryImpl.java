package spring.backend.activity.infrastructure.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import spring.backend.activity.domain.entity.QuickStart;
import spring.backend.activity.domain.repository.QuickStartRepository;
import spring.backend.activity.exception.QuickStartErrorCode;
import spring.backend.activity.infrastructure.mapper.QuickStartMapper;
import spring.backend.activity.infrastructure.persistence.jpa.entity.QuickStartJpaEntity;
import spring.backend.activity.infrastructure.persistence.jpa.repository.QuickStartJpaRepository;

@Repository
@RequiredArgsConstructor
@Log4j2
public class QuickStartRepositoryImpl implements QuickStartRepository {

    private final QuickStartMapper quickStartMapper;
    private final QuickStartJpaRepository quickStartJpaRepository;

    @Override
    public QuickStart findById(Long id) {
        QuickStartJpaEntity quickStartJpaEntity = quickStartJpaRepository.findById(id).orElse(null);
        if (quickStartJpaEntity == null) {
            return null;
        }
        return quickStartMapper.toDomainEntity(quickStartJpaEntity);
    }

    @Override
    public QuickStart save(QuickStart quickStart) {
        try {
            QuickStartJpaEntity quickStartJpaEntity = quickStartMapper.toJpaEntity(quickStart);
            quickStartJpaRepository.save(quickStartJpaEntity);
            return quickStartMapper.toDomainEntity(quickStartJpaEntity);
        } catch (Exception e) {
            log.error("[QuickStartRepositoryImpl] Failed to save quickStart", e);
            throw QuickStartErrorCode.QUICK_START_SAVE_FAILED.toException();
        }
    }
}
