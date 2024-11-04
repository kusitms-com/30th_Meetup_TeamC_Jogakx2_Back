package spring.backend.activity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.member.dto.response.HomeMemberInfoResponse;

public record FinishActivityResponse(

        @Schema(description = "회원 정보")
        HomeMemberInfoResponse member,

        @Schema(description = "활동 정보")
        ActivityInfo activity
) {
}
