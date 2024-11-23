package spring.backend.member.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.dto.request.EditMemberProfileRequest;
import spring.backend.member.exception.MemberErrorCode;

@Tag(name = "Member", description = "멤버")
public interface EditMemberProfileSwagger {

    @Operation(
            summary = "멤버 프로필 수정 API",
            description = "사용자의 프로필을 수정합니다.",
            operationId = "/v1/member/profile"
    )
    @ApiErrorCode({GlobalErrorCode.class})
    ResponseEntity<RestResponse<Boolean>> editMemberProfile(@Parameter(hidden = true) Member member, EditMemberProfileRequest request);
}
