package spring.backend.activity.query.dao;

import spring.backend.activity.dto.response.HomeActivityInfoResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ActivityDao {

    List<HomeActivityInfoResponse> findTodayActivities(UUID memberId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
