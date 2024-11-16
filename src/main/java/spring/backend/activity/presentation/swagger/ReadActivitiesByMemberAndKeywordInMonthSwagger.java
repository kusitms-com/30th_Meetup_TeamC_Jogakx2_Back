package spring.backend.activity.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.activity.domain.value.Keyword;
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
            @Parameter(
                    description = "조회할 연도 (2024 이상)",
                    schema = @Schema(type = "integer", example = "2024", minimum = "2024")
            )
            int year,

            @Parameter(
                    description = "조회할 월 (1~12)",
                    schema = @Schema(type = "integer", example = "7", minimum = "1", maximum = "12")
            )
            int month,

            @Parameter(
                    description = "키워드 카테고리 (예: NATURE, HEALTH)",
                    schema = @Schema(type = "string", example = "NATURE", allowableValues = {
                            "SELF_DEVELOPMENT", "HEALTH", "NATURE", "CULTURE_ART",
                            "ENTERTAINMENT", "RELAXATION", "SOCIAL"
                    })
            )
            Keyword.Category keywordCategory
    );
}
