package spring.backend.member.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.exception.MemberErrorCode;

@Tag(name = "Member", description = "멤버")
public interface ValidateNicknameSwagger {

    @Operation(
            summary = "닉네임 중복 검증 API",
            description = "닉네임이 조건에 충족하지 않거나 중복일 경우 false를 반환합니다.",
            operationId = "/v1/members/check-nickname"
    )
    @ApiErrorCode({GlobalErrorCode.class, MemberErrorCode.class})
    ResponseEntity<RestResponse<Boolean>> validateNickname(@Schema(description = "요청 닉네임", example = "조각조각") String nickname);
}
