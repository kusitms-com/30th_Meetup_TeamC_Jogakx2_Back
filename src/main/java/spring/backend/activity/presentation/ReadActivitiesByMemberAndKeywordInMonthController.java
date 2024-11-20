package spring.backend.activity.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.activity.application.ReadActivitiesByMemberAndKeywordInMonthService;
import spring.backend.activity.dto.request.ActivitiesByMemberAndKeywordInMonthRequest;
import spring.backend.activity.dto.response.ActivitiesByMemberAndKeywordInMonthResponse;
import spring.backend.activity.presentation.swagger.ReadActivitiesByMemberAndKeywordInMonthSwagger;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class ReadActivitiesByMemberAndKeywordInMonthController implements ReadActivitiesByMemberAndKeywordInMonthSwagger {
    private final ReadActivitiesByMemberAndKeywordInMonthService readActivitiesByMemberAndKeywordInMonthService;

    @Authorization
    @GetMapping("/v1/activities")
    public ResponseEntity<RestResponse<ActivitiesByMemberAndKeywordInMonthResponse>> readActivitiesByMemberAndKeywordInMonth(
            @AuthorizedMember Member member,
            @Valid ActivitiesByMemberAndKeywordInMonthRequest activitiesByMemberAndKeywordInMonthRequest
    ) {
        ActivitiesByMemberAndKeywordInMonthResponse activitiesByMemberAndKeywordInMonthResponse = readActivitiesByMemberAndKeywordInMonthService.readActivitiesByMemberAndKeywordInMonth(
                member,
                activitiesByMemberAndKeywordInMonthRequest.year(),
                activitiesByMemberAndKeywordInMonthRequest.month(),
                activitiesByMemberAndKeywordInMonthRequest.keywordCategory()
        );

        return ResponseEntity.ok(new RestResponse<>(activitiesByMemberAndKeywordInMonthResponse));
    }
}
