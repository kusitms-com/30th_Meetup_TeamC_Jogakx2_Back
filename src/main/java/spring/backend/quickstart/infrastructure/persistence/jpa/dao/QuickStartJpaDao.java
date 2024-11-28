package spring.backend.quickstart.infrastructure.persistence.jpa.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.backend.quickstart.dto.response.QuickStartResponse;
import spring.backend.quickstart.infrastructure.persistence.jpa.entity.QuickStartJpaEntity;
import spring.backend.quickstart.query.dao.QuickStartDao;

import java.util.List;
import java.util.UUID;

public interface QuickStartJpaDao extends JpaRepository<QuickStartJpaEntity, Long>, QuickStartDao {

    @Override
    @Query("""
        select new spring.backend.quickstart.dto.response.QuickStartResponse(
            q.id,
            q.name,
            q.startTime,
            q.spareTime,
            q.type
        )
        from QuickStartJpaEntity q
        where q.memberId = :memberId
    """)
    List<QuickStartResponse> findByMemberId(UUID memberId, Sort sort);

    @Override
    @Query("""
        select new spring.backend.quickstart.dto.response.QuickStartResponse(
                q.id,
                q.name,
                q.startTime,
                q.spareTime,
                q.type
        )
        from QuickStartJpaEntity q
        where q.memberId = :memberId
        and (q.startTime > current_time or q.startTime <= current_time)
        order by
            case when q.startTime > current_time then 0 else 1 end,
            q.startTime asc
    """)
    List<QuickStartResponse> findUpcomingQuickStarts(UUID memberId);
}
