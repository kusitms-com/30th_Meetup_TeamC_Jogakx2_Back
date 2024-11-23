package spring.backend.member.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.dto.request.EditMemberProfileRequest;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.member.domain.service.EditMemberProfileService;
import spring.backend.member.presentation.swagger.EditMemberProfileSwagger;
import spring.backend.member.domain.entity.Member;

@RestController
@RequiredArgsConstructor
public class EditMemberProfileController implements EditMemberProfileSwagger {

    private final EditMemberProfileService editMemberProfileService;

    @Override
    @PatchMapping("/v1/member/profile")
    @Authorization
    public ResponseEntity<RestResponse<Boolean>> editMemberProfile(@AuthorizedMember Member member, @Valid @RequestBody EditMemberProfileRequest editMemberProfileRequest) {
        boolean isProfileChanged = editMemberProfileService.edit(member, editMemberProfileRequest);
        return ResponseEntity.ok(new RestResponse<>(isProfileChanged));
    }
}
