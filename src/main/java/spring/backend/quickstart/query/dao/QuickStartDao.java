package spring.backend.quickstart.query.dao;

import org.springframework.data.domain.Sort;
import spring.backend.quickstart.dto.response.QuickStartResponse;

import java.util.List;
import java.util.UUID;

public interface QuickStartDao {

    List<QuickStartResponse> findByMemberId(UUID memberId, Sort sort);

    List<QuickStartResponse> findUpcomingQuickStarts(UUID memberId);
}
