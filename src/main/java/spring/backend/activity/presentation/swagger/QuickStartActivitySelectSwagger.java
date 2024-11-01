package spring.backend.activity.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import spring.backend.activity.dto.request.QuickStartActivitySelectRequest;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.activity.exception.QuickStartErrorCode;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.member.domain.entity.Member;

@Tag(name = "QuickStartActivitySelect", description = "빠른 시작 활동 선택")
public interface QuickStartActivitySelectSwagger {

    @Operation(
            summary = "빠른 시작 활동 선택 API",
            description = "빠른 시작 활동을 선택합니다.",
            operationId = "/v1/quick-starts/{quickStartId}/activities"
    )
    @ApiErrorCode({
            GlobalErrorCode.class, ActivityErrorCode.class, QuickStartErrorCode.class
    })
    Long quickStartUserActivitySelect(@Parameter(hidden = true) Member member, @PathVariable Long quickStartId, QuickStartActivitySelectRequest quickStartActivitySelectRequest);
}
