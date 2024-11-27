package spring.backend.activity.domain.repository;

import spring.backend.activity.domain.entity.QuickStart;

public interface QuickStartRepository {

    QuickStart findById(Long id);
    QuickStart save(QuickStart quickStart);
}
