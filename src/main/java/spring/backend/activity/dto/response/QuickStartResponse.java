package spring.backend.activity.dto.response;

import spring.backend.activity.domain.value.Type;

import java.sql.Time;

public record QuickStartResponse(
        Long id,
        String name,
        Time startTime,
        Integer spareTime,
        Type type
) {
}
