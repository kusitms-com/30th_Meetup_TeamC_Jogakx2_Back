package spring.backend.activity.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.activity.application.QuickStartActivitySelectService;
import spring.backend.activity.dto.request.QuickStartActivitySelectRequest;
import spring.backend.activity.presentation.swagger.QuickStartActivitySelectSwagger;
import spring.backend.core.configuration.argumentresolver.LoginMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class QuickStartActivitySelectController implements QuickStartActivitySelectSwagger {

    private final QuickStartActivitySelectService quickStartActivitySelectService;

    @Override
    @Authorization
    @PostMapping("/v1/quick-starts/{quickStartId}/activities")
    public Long quickStartUserActivitySelect(@LoginMember Member member, @PathVariable Long quickStartId, QuickStartActivitySelectRequest quickStartActivitySelectRequest) {
        // TODO Auto-generated method stub
        Long result = quickStartActivitySelectService.quickStartUserActivitySelect(member, quickStartId, quickStartActivitySelectRequest);
        return result;
    }
}
