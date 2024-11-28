package spring.backend.quickstart.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.quickstart.presentation.dto.request.QuickStartRequest;
import spring.backend.quickstart.exception.QuickStartErrorCode;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.member.domain.entity.Member;

@Tag(name = "QuickStart", description = "빠른 시작")
public interface UpdateQuickStartSwagger {

    @Operation(
            summary = "빠른 시작 수정 API",
            description = "빠른 시작 ID를 통해 빠른 시작을 수정합니다.",
            operationId = "/v1/quick-starts/{quickStartId}"
    )
    @ApiErrorCode({GlobalErrorCode.class, QuickStartErrorCode.class})
    ResponseEntity<Void> updateQuickStart(@Parameter(hidden = true) Member member, QuickStartRequest request, Long quickStartId);
}
