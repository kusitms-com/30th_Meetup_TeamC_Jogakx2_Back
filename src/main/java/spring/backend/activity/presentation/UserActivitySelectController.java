package spring.backend.activity.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.activity.application.UserActivitySelectService;
import spring.backend.activity.dto.request.UserActivitySelectRequest;
import spring.backend.activity.presentation.swagger.UserActivitySelectSwagger;
import spring.backend.core.configuration.argumentresolver.LoginMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class UserActivitySelectController implements UserActivitySelectSwagger {

    private final UserActivitySelectService userActivitySelectService;

    @Authorization
    @PostMapping("/v1/user-activity-selection")
    @Override
    public ResponseEntity<RestResponse<Long>> userActivitySelection(@LoginMember Member member, @Valid @RequestBody UserActivitySelectRequest userActivitySelectRequest) {
        Long savedActivityId = userActivitySelectService.userActivitySelection(member, userActivitySelectRequest);
        return ResponseEntity.ok(new RestResponse<>(savedActivityId));
    }
}
