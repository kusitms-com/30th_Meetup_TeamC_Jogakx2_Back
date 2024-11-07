package spring.backend.activity.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.activity.dto.request.UserActivitySelectRequest;
import spring.backend.activity.dto.response.UserActivitySelectResponse;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@Tag(name = "Activity", description = "활동")
public interface UserActivitySelectSwagger {

    @Operation(
            summary = "사용자 활동 선택 API",
            description = "사용자가 추천받은 활동 중 한가지 활동을 선택합니다.",
            operationId = "/v1/activities"
    )
    @ApiErrorCode({GlobalErrorCode.class, ActivityErrorCode.class})
    ResponseEntity<RestResponse<UserActivitySelectResponse>> userActivitySelect(@Parameter(hidden = true) Member member, UserActivitySelectRequest userActivitySelectRequest);
}
