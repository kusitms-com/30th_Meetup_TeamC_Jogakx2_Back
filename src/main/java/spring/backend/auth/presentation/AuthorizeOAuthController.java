package spring.backend.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import spring.backend.auth.application.AuthorizeOAuthService;

import java.net.URI;

@Controller
@RequestMapping("/v1/oauth")
@RequiredArgsConstructor
public class AuthorizeOAuthController {

    private final AuthorizeOAuthService authorizeOAuthService;

    @GetMapping("/{providerName}")
    public RedirectView authorizeOAuth(@PathVariable String providerName) {
        URI authUrl = authorizeOAuthService.getAuthorizeUrl(providerName);
        return new RedirectView(authUrl.toASCIIString());
    }
}
