package spring.backend.member.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import spring.backend.core.configuration.swagger.ApiErrorCode;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.exception.MemberErrorCode;

@Tag(name = "Member", description = "멤버")
public interface ChangeEmailNotificationSwagger {

    @Operation(
            summary = "이메일 알림 설정 API",
            description = "사용자의 이메일 알림 설정을 변경합니다. \n\n 기본적으로 이메일 알림은 활성화되어 있으며, 요청을 통해 활성화 또는 비활성화할 수 있습니다.",
            operationId = "/v1/members/email-notification"
    )
    @ApiErrorCode({GlobalErrorCode.class, MemberErrorCode.class})
    ResponseEntity<Void> changeEmailNotification(@Parameter(hidden = true) Member member);
}
