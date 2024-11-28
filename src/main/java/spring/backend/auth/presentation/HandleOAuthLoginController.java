package spring.backend.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.backend.auth.application.HandleOAuthLoginService;
import spring.backend.auth.presentation.dto.response.LoginResponse;
import spring.backend.core.presentation.RestResponse;

@RestController
@RequestMapping("/v1/oauth/login")
@RequiredArgsConstructor
public class HandleOAuthLoginController {

    private final HandleOAuthLoginService handleOAuthLoginService;

    @GetMapping("/{providerName}")
    public ResponseEntity<RestResponse<LoginResponse>> handleOAuthLogin(@RequestParam(value = "code", required = false) String code,
                                                                        @RequestParam(value = "state", required = false) String state,
                                                                        @PathVariable String providerName) {
        LoginResponse loginResponse = handleOAuthLoginService.handleOAuthLogin(providerName, code, state);
        return ResponseEntity.ok(new RestResponse<>(loginResponse));
    }
}
