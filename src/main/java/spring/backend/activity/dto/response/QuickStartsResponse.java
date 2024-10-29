package spring.backend.activity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record QuickStartsResponse(

        @Schema(description = "빠른 시작 리스트")
        List<QuickStartResponse> quickStartResponses
) {
}
