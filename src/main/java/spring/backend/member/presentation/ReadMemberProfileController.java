package spring.backend.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.dto.response.MemberProfileResponse;
import spring.backend.member.presentation.swagger.ReadMemberProfileSwagger;

@RestController
@RequestMapping
public class ReadMemberProfileController implements ReadMemberProfileSwagger {

    @Authorization
    @GetMapping("/v1/profile")
    public ResponseEntity<RestResponse<MemberProfileResponse>> readMemberProfile(@AuthorizedMember Member member) {
        return ResponseEntity.ok(new RestResponse<>(MemberProfileResponse.from(member)));
    }
}
