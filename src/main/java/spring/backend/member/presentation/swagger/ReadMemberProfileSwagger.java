package spring.backend.member.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.presentation.dto.response.MemberProfileResponse;

@Tag(name = "Member", description = "멤버")
public interface ReadMemberProfileSwagger {

    @Operation(
            summary = "프로필 조회 API",
            description = "사용자의 프로필을 조회합니다.",
            operationId = "/v1/profile"
    )
    @ApiErrorCode({GlobalErrorCode.class})
    ResponseEntity<RestResponse<MemberProfileResponse>> readMemberProfile(@Parameter(hidden = true) Member member);
}
