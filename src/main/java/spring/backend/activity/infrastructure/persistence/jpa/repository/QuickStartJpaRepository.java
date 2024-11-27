package spring.backend.activity.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.backend.activity.infrastructure.persistence.jpa.entity.QuickStartJpaEntity;

public interface QuickStartJpaRepository extends JpaRepository<QuickStartJpaEntity, Long> {
}
