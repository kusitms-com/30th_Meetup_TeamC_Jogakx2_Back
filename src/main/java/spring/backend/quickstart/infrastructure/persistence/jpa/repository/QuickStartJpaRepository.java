package spring.backend.quickstart.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.backend.quickstart.infrastructure.persistence.jpa.entity.QuickStartJpaEntity;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface QuickStartJpaRepository extends JpaRepository<QuickStartJpaEntity, Long> {

    @Query("""
        select q
        from QuickStartJpaEntity q
        where q.memberId in :memberIds
        and q.startTime between :lowerBound and :upperBound
        order by q.startTime asc
    """)
    List<QuickStartJpaEntity> findEarliestQuickStartsForMembers(List<UUID> memberIds, LocalTime lowerBound,LocalTime upperBound);
}
