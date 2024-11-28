package spring.backend.activity.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.activity.application.ReadMonthlyActivityOverviewService;
import spring.backend.activity.presentation.dto.request.MonthlyActivityOverviewRequest;
import spring.backend.activity.presentation.dto.response.MonthlyActivityOverviewResponse;
import spring.backend.activity.presentation.swagger.ReadMonthlyActivityOverviewSwagger;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class ReadMonthlyActivityOverviewController implements ReadMonthlyActivityOverviewSwagger {
    private final ReadMonthlyActivityOverviewService readMonthlyActivityOverviewService;

    @Authorization
    @GetMapping("/v1/activities/overview")
    public ResponseEntity<RestResponse<MonthlyActivityOverviewResponse>> readMonthlyActivityOverview(
            @AuthorizedMember Member member,
            @Valid MonthlyActivityOverviewRequest monthlyActivityOverviewRequest
    ) {
        MonthlyActivityOverviewResponse monthlyActivityOverviewResponse = readMonthlyActivityOverviewService.readMonthlyActivityOverview(member, monthlyActivityOverviewRequest);
        return ResponseEntity.ok(new RestResponse<>(monthlyActivityOverviewResponse));
    }
}
