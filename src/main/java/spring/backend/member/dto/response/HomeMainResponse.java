package spring.backend.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.activity.dto.response.HomeActivityInfoResponse;
import spring.backend.quickstart.dto.response.QuickStartResponse;

import java.util.List;

public record HomeMainResponse(

        @Schema(description = "회원 정보")
        HomeMemberInfoResponse member,

        @Schema(description = "빠른 시작")
        QuickStartResponse quickStart,

        @Schema(description = "총 모은 시간", example = "120")
        int totalSavedTime,

        @Schema(description = "활동 목록")
        List<HomeActivityInfoResponse> activities
) {
    public static HomeMainResponse of(HomeMemberInfoResponse member, QuickStartResponse quickStart, int totalSavedTime, List<HomeActivityInfoResponse> activities) {
        return new HomeMainResponse(member, quickStart, totalSavedTime, activities);
    }
}
