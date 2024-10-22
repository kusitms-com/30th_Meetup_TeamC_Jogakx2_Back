package spring.backend.auth.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.auth.application.OnboardingSignUpService;
import spring.backend.auth.dto.request.OnboardingSignUpRequest;
import spring.backend.auth.presentation.swagger.OnboardingSignUpSwagger;
import spring.backend.core.configuration.argumentresolver.LoginMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.entity.Member;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OnboardingSignUpController implements OnboardingSignUpSwagger {

    private final OnboardingSignUpService onboardingSignUpService;

    @Authorization
    @PostMapping("/v1/members/onboard")
    public ResponseEntity<RestResponse<UUID>> onboardingSignUp(@LoginMember Member member, @Valid @RequestBody OnboardingSignUpRequest request) {
        Member updatedMember = onboardingSignUpService.onboardingSignUp(member, request);
        return ResponseEntity.ok(new RestResponse<>(updatedMember.getId()));
    }
}
