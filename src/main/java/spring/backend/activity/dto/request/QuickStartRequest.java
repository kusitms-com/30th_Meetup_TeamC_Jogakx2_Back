package spring.backend.activity.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import spring.backend.activity.domain.value.Type;

import java.sql.Time;

public record QuickStartRequest(

        @NotNull(message = "이름은 필수 입력 항목입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,10}$", message = "이름은 한글, 영문, 숫자만 입력 가능하며 최대 10자까지 입력 가능합니다.")
        String name,

        @NotNull(message = "시작 시간은 필수 입력 항목입니다.")
        Time startTime,

        @NotNull(message = "자투리 시간은 필수 입력 항목입니다.")
        @Min(value = 10, message = "자투리 시간은 최소 10이어야 합니다.")
        @Max(value = 300, message = "자투리 시간은 최대 300이어야 합니다.")
        Integer spareTime,

        @NotNull(message = "활동 유형은 필수 입력 항목입니다.")
        Type type
) {
}
