package spring.backend.activity.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.activity.presentation.dto.response.FinishActivityResponse;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@Tag(name = "Activity", description = "활동")
public interface FinishActivitySwagger {

    @Operation(
            summary = "활동 종료 API",
            description = "진행 중인 활동을 종료합니다. \n\n 이 API는 활동이 자투리 시간 내에서만 종료될 수 있습니다.",
            operationId = "/v1/activities/{activityId}/finish"
    )
    @ApiErrorCode({GlobalErrorCode.class, ActivityErrorCode.class})
    ResponseEntity<RestResponse<FinishActivityResponse>> finishActivity(@Parameter(hidden = true) Member member, Long activityId);
}
