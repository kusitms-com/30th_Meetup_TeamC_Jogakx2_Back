package spring.backend.activity.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.activity.dto.request.MonthlyActivityOverviewRequest;
import spring.backend.activity.dto.response.MonthlyActivityOverviewResponse;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@Tag(name = "Activity" , description = "활동")
public interface ReadMonthlyActivityOverviewSwagger {
    @Operation(
            summary = "월간 활동 개요 조회 API",
            description = "사용자의 월간 활동 개요를 조회합니다.",
            operationId = "/v1/activity"
    )
    ResponseEntity<RestResponse<MonthlyActivityOverviewResponse>> readMonthlyActivityOverview(@Parameter(hidden = true) Member member, MonthlyActivityOverviewRequest monthlyActivityOverviewRequest);
}
