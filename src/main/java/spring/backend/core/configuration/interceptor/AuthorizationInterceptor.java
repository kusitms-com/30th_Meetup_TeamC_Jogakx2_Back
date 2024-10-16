package spring.backend.core.configuration.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.core.application.JwtService;

import java.lang.annotation.Annotation;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORIZATION_BEARER_PREFIX = "Bearer";

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            return true;
        }
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Annotation authorizationAnnotation = handlerMethod.getMethodAnnotation(Authorization.class);
            if (authorizationAnnotation != null) {
                String token = extractToken(authorizationHeader);
                jwtService.validateTokenExpiration(token);
            }
        }
        return true;
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader == null) {
            throw AuthenticationErrorCode.NOT_EXIST_HEADER.toException();
        }
        try {
            return authorizationHeader.split(AUTHORIZATION_BEARER_PREFIX)[1].replace(" ", "");
        } catch (Exception e) {
            throw AuthenticationErrorCode.NOT_EXIST_TOKEN.toException();
        }
    }
}
