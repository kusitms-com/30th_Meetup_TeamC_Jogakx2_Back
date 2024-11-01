package spring.backend.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.member.domain.entity.Member;

import java.util.UUID;

public record HomeMemberInfoResponse(

        @Schema(description = "멤버 ID", example = "4d45be4b-1cb0-4760-b826-7afc505783cd")
        UUID id,

        @Schema(description = "닉네임", example = "조각조각")
        String nickname,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/image.jpg")
        String profileImage
) {
    public static HomeMemberInfoResponse from(Member member) {
        return new HomeMemberInfoResponse(member.getId(), member.getNickname(), member.getProfileImage());
    }
}
