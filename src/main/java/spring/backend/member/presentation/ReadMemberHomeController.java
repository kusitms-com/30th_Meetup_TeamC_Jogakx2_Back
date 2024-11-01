package spring.backend.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.application.ReadMemberHomeService;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.dto.response.HomeMainResponse;
import spring.backend.member.presentation.swagger.ReadMemberHomeSwagger;

@RestController
@RequiredArgsConstructor
public class ReadMemberHomeController implements ReadMemberHomeSwagger {

    private final ReadMemberHomeService readMemberHomeService;

    @Authorization
    @GetMapping("/v1/home")
    public ResponseEntity<RestResponse<HomeMainResponse>> readMemberHome(@AuthorizedMember Member member) {
        return ResponseEntity.ok(new RestResponse<>(readMemberHomeService.readMemberHome(member)));
    }
}
