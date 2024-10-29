package spring.backend.activity.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.activity.application.UpdateQuickStartService;
import spring.backend.activity.dto.request.QuickStartRequest;
import spring.backend.core.configuration.argumentresolver.LoginMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class UpdateQuickStartController {

    private final UpdateQuickStartService updateQuickStartService;

    @Authorization
    @PatchMapping("/v1/quick-starts/{quickStartId}")
    public ResponseEntity<Void> updateQuickStart(@LoginMember Member member, @Valid @RequestBody QuickStartRequest request, @PathVariable Long quickStartId) {
        updateQuickStartService.updateQuickStart(member, request, quickStartId);
        return ResponseEntity.ok().build();
    }
}
