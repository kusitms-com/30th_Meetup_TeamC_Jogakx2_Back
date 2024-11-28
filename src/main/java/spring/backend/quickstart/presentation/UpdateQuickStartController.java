package spring.backend.quickstart.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.quickstart.domain.service.UpdateQuickStartService;
import spring.backend.quickstart.dto.request.QuickStartRequest;
import spring.backend.quickstart.presentation.swagger.UpdateQuickStartSwagger;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class UpdateQuickStartController implements UpdateQuickStartSwagger {

    private final UpdateQuickStartService updateQuickStartService;

    @Authorization
    @PatchMapping("/v1/quick-starts/{quickStartId}")
    public ResponseEntity<Void> updateQuickStart(@AuthorizedMember Member member, @Valid @RequestBody QuickStartRequest request, @PathVariable Long quickStartId) {
        updateQuickStartService.updateQuickStart(member, request, quickStartId);
        return ResponseEntity.ok().build();
    }
}
