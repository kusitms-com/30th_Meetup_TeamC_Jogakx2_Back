package spring.backend.activity.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.activity.presentation.dto.request.QuickStartActivitySelectRequest;
import spring.backend.activity.presentation.dto.response.QuickStartActivitySelectResponse;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.quickstart.exception.QuickStartErrorCode;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@Tag(name = "Activity", description = "활동")
public interface QuickStartActivitySelectSwagger {

    @Operation(
            summary = "빠른 시작 활동 선택 API",
            description = "빠른 시작 활동을 선택합니다.",
            operationId = "/v1/quick-starts/{quickStartId}/activities"
    )
    @ApiErrorCode({
            GlobalErrorCode.class, ActivityErrorCode.class, QuickStartErrorCode.class
    })
    ResponseEntity<RestResponse<QuickStartActivitySelectResponse>> quickStartUserActivitySelect(@Parameter(hidden = true) Member member, Long quickStartId, QuickStartActivitySelectRequest quickStartActivitySelectRequest);
}
