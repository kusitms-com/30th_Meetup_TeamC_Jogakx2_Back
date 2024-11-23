package spring.backend.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.core.configuration.argumentresolver.AuthorizedMember;
import spring.backend.core.configuration.interceptor.Authorization;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.service.ChangeEmailNotificationService;

@RestController
@RequiredArgsConstructor
public class ChangeEmailNotificationController {

    private final ChangeEmailNotificationService changeEmailNotificationService;

    @Authorization
    @PatchMapping("/v1/members/email-notification")
    public ResponseEntity<Void> changeEmailNotification(@AuthorizedMember Member member) {
        changeEmailNotificationService.changeEmailNotification(member);
        return ResponseEntity.ok().build();
    }
}
