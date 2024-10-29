package spring.backend.activity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.activity.domain.value.Type;

import java.sql.Time;

public record QuickStartResponse(

        @Schema(description = "빠른 시작 ID", example = "1")
        Long id,

        @Schema(description = "빠른 시작 이름", example = "등교")
        String name,

        @Schema(description = "시작 시간", example = "12:30:00")
        Time startTime,

        @Schema(description = "자투리 시간", example = "300")
        Integer spareTime,

        @Schema(description = "활동 유형 (ONLINE, OFFLINE, ONLINE_AND_OFFLINE)", example = "ONLINE")
        Type type
) {
}
