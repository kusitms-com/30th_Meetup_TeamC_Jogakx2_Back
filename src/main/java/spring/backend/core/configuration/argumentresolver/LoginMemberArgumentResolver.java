package spring.backend.core.configuration.argumentresolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.core.application.JwtService;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.exception.MemberErrorCode;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORIZATION_BEARER_PREFIX = "Bearer";

    private final JwtService jwtService;

    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorizationHeader = webRequest.getHeader(AUTHORIZATION_HEADER);
        String token = extractToken(authorizationHeader);
        UUID memberId = jwtService.extractMemberId(token);
        Member member = memberRepository.findById(memberId);
        return Optional.ofNullable(member).orElseThrow(MemberErrorCode.NOT_EXIST_MEMBER::toException);
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
