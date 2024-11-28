package spring.backend.activity.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import spring.backend.activity.presentation.dto.request.ReadActivityCalendarRequest;
import spring.backend.activity.presentation.dto.response.ActivityCalendarResponse;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@Tag(name = "Activity", description = "활동")
public interface ReadActivityCalendarSwagger {

    @Operation(
            summary = "활동 캘린더 조회 API",
            description = "사용자가 연월을 선택하여 월별 활동 요약과 월별 활동 상세 정보 리스트를 반환합니다.",
            operationId = "/v1/activity-calendar"
    )
    @ApiErrorCode({GlobalErrorCode.class, ActivityErrorCode.class})
    ResponseEntity<RestResponse<ActivityCalendarResponse>> readActivityCalendar(@Parameter(hidden = true) Member member, @ParameterObject ReadActivityCalendarRequest request);
}
