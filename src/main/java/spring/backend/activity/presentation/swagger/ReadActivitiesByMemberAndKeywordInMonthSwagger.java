package spring.backend.activity.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.dto.request.ActivitiesByMemberAndKeywordInMonthRequest;
import spring.backend.activity.dto.response.ActivitiesByMemberAndKeywordInMonthResponse;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@Tag(name = "Activity", description = "활동")
public interface ReadActivitiesByMemberAndKeywordInMonthSwagger {
    @Operation(
            summary = "특정 달 선택한 키워드 활동 조회 API",
            description = "특정 달 선택한 키워드 활동을 조회합니다.",
            operationId = "/v1/activities"
    )
    ResponseEntity<RestResponse<ActivitiesByMemberAndKeywordInMonthResponse>> readActivitiesByMemberAndKeywordInMonth(
            @Parameter(hidden = true) Member member, ActivitiesByMemberAndKeywordInMonthRequest activitiesByMemberAndKeywordInMonthRequest
    );
}
