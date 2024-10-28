package spring.backend.recommendation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import spring.backend.recommendation.infrastructure.clova.domain.value.Type;

@Getter
public class UserInputRequest {

    @NotNull
    @Pattern(regexp = "^(5|[1-9][0-9]|[1-2][0-9]{2}|300)$", message = "자투리 시간은 5부터 300 사이의 숫자로 입력해주세요.")
    @Schema(description = "자투리 시간", example = "30")
    private int spareTime;

    @NotNull
    @Schema(description = "활동 타입", example = "OFFLINE")
    private Type activityType;

    @NotNull
    @Schema(description = "활동 키워드", example = "문화/예술")
    private String keyword;

    @Schema(description = "위치", example = "서울시 강남구")
    private String location;
}