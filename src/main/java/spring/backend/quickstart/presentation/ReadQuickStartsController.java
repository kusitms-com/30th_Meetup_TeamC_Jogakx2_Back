package spring.backend.quickstart.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.quickstart.application.ReadQuickStartsService;
import spring.backend.quickstart.dto.response.QuickStartsResponse;
import spring.backend.quickstart.presentation.swagger.ReadQuickStartsSwagger;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class ReadQuickStartsController implements ReadQuickStartsSwagger {

    private final ReadQuickStartsService readQuickStartsService;

    @Authorization
    @GetMapping("/v1/quick-starts")
    public ResponseEntity<RestResponse<QuickStartsResponse>> readQuickStarts(@AuthorizedMember Member member) {
        return ResponseEntity.ok(new RestResponse<>(readQuickStartsService.readQuickStarts(member)));
    }
}
