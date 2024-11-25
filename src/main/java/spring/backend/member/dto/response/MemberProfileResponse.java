package spring.backend.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.member.domain.entity.Member;

public record MemberProfileResponse(

        @Schema(description = "이메일", example = "jogakjogak@gmail.com")
        String email,

        @Schema(description = "이메일 알림 설정 여부", example = "true")
        boolean isEmailNotificationEnabled
) {
    public static MemberProfileResponse from(Member member) {
        return new MemberProfileResponse(member.getEmail(), member.isEmailNotification());
    }
}
