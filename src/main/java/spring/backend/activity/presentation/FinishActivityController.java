package spring.backend.activity.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.activity.domain.service.FinishActivityService;
import spring.backend.activity.dto.response.FinishActivityResponse;
import spring.backend.activity.presentation.swagger.FinishActivitySwagger;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class FinishActivityController implements FinishActivitySwagger {

    private final FinishActivityService finishActivityService;

    @Authorization
    @PatchMapping("/v1/activities/{activityId}/finish")
    public ResponseEntity<RestResponse<FinishActivityResponse>> finishActivity(@AuthorizedMember Member member, @PathVariable Long activityId) {
        return ResponseEntity.ok(new RestResponse<>(finishActivityService.finishActivity(member, activityId)));
    }
}
