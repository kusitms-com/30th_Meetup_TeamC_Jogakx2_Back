package spring.backend.activity.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.backend.activity.infrastructure.persistence.jpa.entity.ActivityJpaEntity;

public interface ActivityJpaRepository extends JpaRepository<ActivityJpaEntity, Long> {
}
