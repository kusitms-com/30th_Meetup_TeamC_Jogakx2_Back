package spring.backend.core.configuration.argumentresolver;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.exception.MemberErrorCode;

@Component
@RequiredArgsConstructor
public class AuthorizedMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final LoginMemberArgumentResolver loginMemberArgumentResolver;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorizedMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Member authorizedMember = (Member) loginMemberArgumentResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        if (authorizedMember == null || !authorizedMember.isMember()) {
            throw MemberErrorCode.NOT_AUTHORIZED_MEMBER.toException();
        }
        return authorizedMember;
    }
}
