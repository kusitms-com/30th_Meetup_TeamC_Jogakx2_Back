package spring.backend.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.core.presentation.RestResponse;
import spring.backend.member.domain.service.ValidateNicknameService;
import spring.backend.member.presentation.swagger.ValidateNicknameSwagger;

@RestController
@RequiredArgsConstructor
public class ValidateNicknameController implements ValidateNicknameSwagger {

    private final ValidateNicknameService validateNicknameService;

    @GetMapping("/v1/members/check-nickname")
    public ResponseEntity<RestResponse<Boolean>> validateNickname(String nickname) {
        boolean isValidNickname = validateNicknameService.validateNickname(nickname);
        return ResponseEntity.ok(new RestResponse<>(isValidNickname));
    }
}
