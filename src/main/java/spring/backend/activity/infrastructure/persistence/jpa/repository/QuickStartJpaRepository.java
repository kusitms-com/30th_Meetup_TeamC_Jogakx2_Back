package spring.backend.activity.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.backend.activity.infrastructure.persistence.jpa.entity.QuickStartJpaEntity;

import java.time.LocalTime;
import java.util.List;

public interface QuickStartJpaRepository extends JpaRepository<QuickStartJpaEntity, Long> {

    @Query("""
        select q
        from QuickStartJpaEntity q
        where q.startTime = (
            select MIN(q2.startTime)
            from QuickStartJpaEntity q2
            where q2.memberId = q.memberId
            and q2.startTime between :lowerBound and :upperBound
            group by q2.memberId
        )
    """)
    List<QuickStartJpaEntity> findQuickStartsWithinTimeRange(LocalTime lowerBound, LocalTime upperBound);
}
