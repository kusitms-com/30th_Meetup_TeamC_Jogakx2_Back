package spring.backend.member.dto.response;

import spring.backend.activity.dto.response.HomeActivityInfoResponse;
import spring.backend.activity.dto.response.QuickStartResponse;

import java.util.List;

public record HomeMainResponse(
        HomeMemberInfoResponse member,
        QuickStartResponse quickStart,
        int totalSavedTime,
        List<HomeActivityInfoResponse> activities
) {
    public static HomeMainResponse of(HomeMemberInfoResponse member, QuickStartResponse quickStart, int totalSavedTime, List<HomeActivityInfoResponse> activities) {
        return new HomeMainResponse(member, quickStart, totalSavedTime, activities);
    }
}
