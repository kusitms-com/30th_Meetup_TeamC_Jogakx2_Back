package spring.backend.auth.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.auth.presentation.dto.request.OnboardingSignUpRequest;
import spring.backend.auth.presentation.dto.response.OnboardingSignUpResponse;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.exception.MemberErrorCode;

@Tag(name = "Auth", description = "인증/인가")
public interface OnboardingSignUpSwagger {

    @Operation(
            summary = "가입 온보딩 API",
            description = "사용자가 닉네임, 나이, 성별, 프로필사진을 입력하여 회원가입을 진행합니다",
            operationId = "/v1/members/onboard"
    )
    @ApiErrorCode({GlobalErrorCode.class, AuthenticationErrorCode.class, MemberErrorCode.class})
    ResponseEntity<RestResponse<OnboardingSignUpResponse>> onboardingSignUp(@Parameter(hidden = true) Member member, OnboardingSignUpRequest request);
}
