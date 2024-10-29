package spring.backend.activity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import spring.backend.activity.domain.value.Type;

import java.sql.Time;

public record QuickStartRequest(

        @NotNull(message = "이름은 필수 입력 항목입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,10}$", message = "이름은 한글, 영문, 숫자만 입력 가능하며 최대 10자까지 입력 가능합니다.")
        @Schema(description = "빠른 시작 이름", example = "등교")
        String name,

        @NotNull(message = "시작 시간은 필수 입력 항목입니다.")
        @Schema(description = "시작 시간", example = "12:30:00")
        Time startTime,

        @NotNull(message = "자투리 시간은 필수 입력 항목입니다.")
        @Min(value = 10, message = "자투리 시간은 최소 10이어야 합니다.")
        @Max(value = 300, message = "자투리 시간은 최대 300이어야 합니다.")
        @Schema(description = "자투리 시간", example = "300")
        Integer spareTime,

        @NotNull(message = "활동 유형은 필수 입력 항목입니다.")
        @Schema(description = "활동 유형 (ONLINE, OFFLINE, ONLINE_AND_OFFLINE)", example = "ONLINE")
        Type type
) {
}
