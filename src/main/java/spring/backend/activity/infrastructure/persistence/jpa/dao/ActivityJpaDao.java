package spring.backend.activity.infrastructure.persistence.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.backend.activity.dto.response.HomeActivityInfoResponse;
import spring.backend.activity.infrastructure.persistence.jpa.entity.ActivityJpaEntity;
import spring.backend.activity.query.dao.ActivityDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ActivityJpaDao extends JpaRepository<ActivityJpaEntity, Long>, ActivityDao {

    @Override
    @Query("""
        select new spring.backend.activity.dto.response.HomeActivityInfoResponse(
            a.id,
            a.keyword,
            a.title,
            a.savedTime
        )
        from ActivityJpaEntity a
        where a.memberId = :memberId
        and a.createdAt between :startDateTime and :endDateTime
        and a.finished = true
        order by a.createdAt ASC
    """)
    List<HomeActivityInfoResponse> findTodayActivities(UUID memberId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}