package spring.backend.quickstart.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.quickstart.domain.service.CreateQuickStartService;
import spring.backend.quickstart.dto.request.QuickStartRequest;
import spring.backend.quickstart.presentation.swagger.CreateQuickStartSwagger;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class CreateQuickStartController implements CreateQuickStartSwagger {

    private final CreateQuickStartService createQuickStartService;

    @Authorization
    @PostMapping("/v1/quick-starts")
    public ResponseEntity<RestResponse<Long>> createQuickStart(@AuthorizedMember Member member, @Valid @RequestBody QuickStartRequest request) {
        Long memberId = createQuickStartService.createQuickStart(member, request);
        return ResponseEntity.ok(new RestResponse<>(memberId));
    }
}
