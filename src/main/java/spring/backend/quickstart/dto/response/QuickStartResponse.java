package spring.backend.quickstart.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.activity.domain.value.Type;
import spring.backend.core.util.TimeUtil;

import java.time.LocalTime;

public record QuickStartResponse(

        @Schema(description = "빠른 시작 ID", example = "1")
        Long id,

        @Schema(description = "빠른 시작 이름", example = "등교")
        String name,

        @Schema(description = "시작 시간", example = "12:30")
        LocalTime startTime,

        @Schema(description = "자투리 시간", example = "300")
        Integer spareTime,

        @Schema(description = "활동 유형 (ONLINE, OFFLINE, ONLINE_AND_OFFLINE)", example = "ONLINE")
        Type type,

        @Schema(description = "시작 시간의 시", example = "12")
        Integer hour,

        @Schema(description = "시작 시간의 분", example = "30")
        Integer minute,

        @Schema(description = "오전/오후 표시", example = "오후")
        String meridiem
) {
        public QuickStartResponse(Long id, String name, LocalTime startTime, Integer spareTime, Type type) {
                this(id, name, startTime, spareTime, type, TimeUtil.toHour(startTime), TimeUtil.toMinute(startTime), TimeUtil.toMeridiem(startTime));
        }
}
