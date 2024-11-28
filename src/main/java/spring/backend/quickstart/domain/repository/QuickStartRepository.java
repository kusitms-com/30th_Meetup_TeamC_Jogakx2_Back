package spring.backend.quickstart.domain.repository;

import spring.backend.quickstart.domain.entity.QuickStart;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface QuickStartRepository {

    QuickStart findById(Long id);
    QuickStart save(QuickStart quickStart);
    List<QuickStart> findEarliestQuickStartsForMembers(List<UUID> memberIds, LocalTime lowerBound, LocalTime upperBound);
}
