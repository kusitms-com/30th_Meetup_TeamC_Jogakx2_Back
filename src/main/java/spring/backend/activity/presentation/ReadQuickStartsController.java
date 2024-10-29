package spring.backend.activity.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.activity.application.ReadQuickStartsService;
import spring.backend.activity.dto.response.QuickStartsResponse;
import spring.backend.core.configuration.argumentresolver.LoginMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class ReadQuickStartsController {

    private final ReadQuickStartsService readQuickStartsService;

    @Authorization
    @GetMapping("/v1/quick-starts")
    public ResponseEntity<RestResponse<QuickStartsResponse>> readQuickStarts(@LoginMember Member member) {
        return ResponseEntity.ok(new RestResponse<>(readQuickStartsService.readQuickStarts(member)));
    }
}
