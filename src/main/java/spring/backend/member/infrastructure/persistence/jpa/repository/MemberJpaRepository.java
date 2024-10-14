package spring.backend.member.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.backend.member.infrastructure.persistence.jpa.entity.MemberJpaEntity;

import java.util.List;
import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, UUID> {

    MemberJpaEntity findByEmail(String email);

    List<MemberJpaEntity> findAllByEmail(String email);
}
