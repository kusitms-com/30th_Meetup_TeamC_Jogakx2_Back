package spring.backend.activity.domain.repository;

import spring.backend.activity.domain.entity.Activity;

public interface ActivityRepository {

    Activity findById(Long id);
    Activity save(Activity Activity);
}
