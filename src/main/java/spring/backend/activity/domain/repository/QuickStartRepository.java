package spring.backend.activity.domain.repository;

import spring.backend.activity.domain.entity.QuickStart;

import java.time.LocalTime;
import java.util.List;

public interface QuickStartRepository {

    QuickStart findById(Long id);
    QuickStart save(QuickStart quickStart);
    List<QuickStart> findQuickStartsWithinTimeRange(LocalTime lowerBound, LocalTime upperBound);
}
