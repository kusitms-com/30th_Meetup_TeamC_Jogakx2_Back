package spring.backend.activity.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.activity.application.ReadActivitiesByMemberAndKeywordInMonthService;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.dto.response.ActivitiesByMemberAndKeywordInMonthResponse;
import spring.backend.activity.presentation.swagger.ReadActivitiesByMemberAndKeywordInMonthSwagger;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
@Validated
public class ReadActivitiesByMemberAndKeywordInMonthController implements ReadActivitiesByMemberAndKeywordInMonthSwagger{
    private final ReadActivitiesByMemberAndKeywordInMonthService readActivitiesByMemberAndKeywordInMonthService;

    @Authorization
    @GetMapping("/v1/activities/{year}/{month}/keyword/{keywordCategory}")
    public ResponseEntity<RestResponse<ActivitiesByMemberAndKeywordInMonthResponse>> readActivitiesByMemberAndKeywordInMonth(
            @AuthorizedMember Member member,
            @PathVariable int year,
            @PathVariable int month,
            @PathVariable Keyword.Category keywordCategory
            ) {
        ActivitiesByMemberAndKeywordInMonthResponse activitiesByMemberAndKeywordInMonthResponse = readActivitiesByMemberAndKeywordInMonthService.readActivitiesByMemberAndKeywordInMonth(member, year, month, keywordCategory);
        return ResponseEntity.ok(new RestResponse<>(activitiesByMemberAndKeywordInMonthResponse));
    }
}
