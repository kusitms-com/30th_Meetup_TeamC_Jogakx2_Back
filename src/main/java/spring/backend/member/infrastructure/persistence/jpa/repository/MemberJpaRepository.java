package spring.backend.member.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.backend.member.domain.value.Role;
import spring.backend.member.infrastructure.persistence.jpa.entity.MemberJpaEntity;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, UUID> {

    MemberJpaEntity findByEmail(String email);

    List<MemberJpaEntity> findAllByEmail(String email);

    boolean existsByNicknameAndRole(String nickname, Role role);

    @Query("""
        select distinct m
        from MemberJpaEntity m
        join QuickStartJpaEntity q on q.memberId = m.id
        where q.startTime between :lowerBound and :upperBound
        and m.emailNotification = true
    """)
    List<MemberJpaEntity> findMembersForQuickStartsInTimeRange(LocalTime lowerBound, LocalTime upperBound);
}
