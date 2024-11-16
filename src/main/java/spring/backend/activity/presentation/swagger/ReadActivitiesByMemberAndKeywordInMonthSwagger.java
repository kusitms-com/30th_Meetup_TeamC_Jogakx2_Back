package spring.backend.activity.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.activity.dto.request.ActivitiesByMemberAndKeywordInMonthRequest;
import spring.backend.activity.dto.response.ActivitiesByMemberAndKeywordInMonthResponse;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@Tag(name = "Activity", description = "활동")
public interface ReadActivitiesByMemberAndKeywordInMonthSwagger {
    @Operation(
            summary = "이번 달 해당 키워드 활동 조회 API",
            description = "이번 달 해당 키워드 활동을 조회합니다.",
            operationId = "/v1/activities/{year}/{month}/keywords/{keywordCategory}"
    )
    ResponseEntity<RestResponse<ActivitiesByMemberAndKeywordInMonthResponse>> readActivitiesByMemberAndKeywordInMonth(
            @Parameter(hidden = true) Member member,
            ActivitiesByMemberAndKeywordInMonthRequest activitiesByMemberAndKeywordInMonthRequest
    );
}
