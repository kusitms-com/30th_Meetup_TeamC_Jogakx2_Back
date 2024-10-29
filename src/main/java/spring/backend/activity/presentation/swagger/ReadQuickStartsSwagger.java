package spring.backend.activity.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.activity.dto.response.QuickStartsResponse;
import spring.backend.activity.exception.QuickStartErrorCode;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@Tag(name = "QuickStart", description = "빠른 시작")
public interface ReadQuickStartsSwagger {

    @Operation(
            summary = "빠른 시작 리스트 조회 API",
            description = "사용자가 생성한 빠른 시작 리스트를 반환합니다.",
            operationId = "/v1/quick-starts"
    )
    @ApiErrorCode({GlobalErrorCode.class, QuickStartErrorCode.class})
    ResponseEntity<RestResponse<QuickStartsResponse>> readQuickStarts(@Parameter(hidden = true) Member member);
}
