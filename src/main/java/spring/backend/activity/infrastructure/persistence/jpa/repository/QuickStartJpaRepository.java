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
        where q.startTime between :lowerBound and :upperBound
        and q.id in (
            select min(q2.id)
            from QuickStartJpaEntity q2
            where q2.memberId = q.memberId
            group by q2.memberId
        )
    """)
    List<QuickStartJpaEntity> findQuickStartsWithinTimeRange(LocalTime lowerBound, LocalTime upperBound);
}
