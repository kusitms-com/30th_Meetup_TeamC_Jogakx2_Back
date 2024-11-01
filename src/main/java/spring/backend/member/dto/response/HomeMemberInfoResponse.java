package spring.backend.member.dto.response;

import spring.backend.member.domain.entity.Member;

import java.util.UUID;

public record HomeMemberInfoResponse(
        UUID id,
        String nickname,
        String profileImage
) {
    public static HomeMemberInfoResponse from(Member member) {
        return new HomeMemberInfoResponse(member.getId(), member.getNickname(), member.getProfileImage());
    }
}
