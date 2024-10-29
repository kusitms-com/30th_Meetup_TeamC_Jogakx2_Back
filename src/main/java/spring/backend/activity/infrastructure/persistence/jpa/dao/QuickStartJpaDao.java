package spring.backend.activity.infrastructure.persistence.jpa.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.backend.activity.dto.response.QuickStartResponse;
import spring.backend.activity.infrastructure.persistence.jpa.entity.QuickStartJpaEntity;
import spring.backend.activity.query.dao.QuickStartDao;

import java.util.List;
import java.util.UUID;

public interface QuickStartJpaDao extends JpaRepository<QuickStartJpaEntity, Long>, QuickStartDao {

    @Override
    @Query("""
        select new spring.backend.activity.dto.response.QuickStartResponse(
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
}