package spring.backend.member.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.presentation.dto.response.HomeMainResponse;
import spring.backend.member.exception.MemberErrorCode;

@Tag(name = "Member", description = "멤버")
public interface ReadMemberHomeSwagger {

    @Operation(
            summary = "홈 메인페이지 조회 API",
            description = "사용자의 가장 근접한 빠른시작과 당일 모은 활동내역을 보여줍니다.",
            operationId = "/v1/home"
    )
    @ApiErrorCode({GlobalErrorCode.class, MemberErrorCode.class})
    ResponseEntity<RestResponse<HomeMainResponse>> readMemberHome(@Parameter(hidden = true) Member member);
}
