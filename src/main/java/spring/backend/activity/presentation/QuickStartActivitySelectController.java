package spring.backend.activity.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.activity.application.QuickStartActivitySelectService;
import spring.backend.activity.dto.request.QuickStartActivitySelectRequest;
import spring.backend.activity.dto.response.QuickStartActivitySelectResponse;
import spring.backend.activity.presentation.swagger.QuickStartActivitySelectSwagger;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class QuickStartActivitySelectController implements QuickStartActivitySelectSwagger {

    private final QuickStartActivitySelectService quickStartActivitySelectService;

    @Override
    @Authorization
    @PostMapping("/v1/quick-starts/{quickStartId}/activities")
    public ResponseEntity<RestResponse<QuickStartActivitySelectResponse>> quickStartUserActivitySelect(@AuthorizedMember Member member, @PathVariable Long quickStartId, @Valid @RequestBody QuickStartActivitySelectRequest quickStartActivitySelectRequest) {
        QuickStartActivitySelectResponse savedActivityIdCreatedByQuickStartResponse = quickStartActivitySelectService.quickStartUserActivitySelect(member, quickStartId, quickStartActivitySelectRequest);
        return ResponseEntity.ok(new RestResponse<>(savedActivityIdCreatedByQuickStartResponse));
    }
}
