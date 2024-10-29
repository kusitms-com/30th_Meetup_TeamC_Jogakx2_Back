package spring.backend.activity.query.dao;

import org.springframework.data.domain.Sort;
import spring.backend.activity.dto.response.QuickStartResponse;

import java.util.List;
import java.util.UUID;

public interface QuickStartDao {

    List<QuickStartResponse> findByMemberId(UUID memberId, Sort sort);
}
