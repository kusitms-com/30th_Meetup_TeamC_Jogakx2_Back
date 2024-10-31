package spring.backend.core.configuration.argumentresolver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import spring.backend.core.exception.DomainException;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.value.Role;
import spring.backend.member.exception.MemberErrorCode;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorizedMemberArgumentResolverTest {

    @InjectMocks
    private AuthorizedMemberArgumentResolver authorizedMemberArgumentResolver;

    @Mock
    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    @Mock
    private NativeWebRequest webRequest;

    @Mock
    private ModelAndViewContainer mavContainer;

    private Member member;
    private Member guest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        member = Member.builder()
                .id(UUID.randomUUID())
                .role(Role.MEMBER)
                .build();
        guest = Member.builder()
                .id(UUID.randomUUID())
                .role(Role.GUEST)
                .build();
    }

    @DisplayName("AuthorizedMember 어노테이션이 있는 경우 지원한다")
    @Test
    public void supportsParameterReturnsTrueForLoginMember() {
        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.hasParameterAnnotation(AuthorizedMember.class)).thenReturn(true);
        Assertions.assertTrue(authorizedMemberArgumentResolver.supportsParameter(parameter));
    }

    @DisplayName("Authorization 헤더에 유효한 토큰이 있을 때 AuthorizedMember 객체를 반환한다")
    @Test
    public void returnsAuthorizedMemberObject_whenAuthorizationHeaderIsProvided() throws Exception {
        // when
        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.hasParameterAnnotation(AuthorizedMember.class)).thenReturn(true);
        when(loginMemberArgumentResolver.resolveArgument(parameter, mavContainer, webRequest, null)).thenReturn(member);

        // then
        Object result = authorizedMemberArgumentResolver.resolveArgument(parameter, mavContainer, webRequest, null);
        assertNotNull(result);
        assertThat(result).isEqualTo(member);
    }

    @DisplayName("Authorization 헤더에 유효한 토큰이 있을 때 Guest인 경우 예외를 발생시킨다")
    @Test
    public void throwsNotAuthorizedMemberException_whenGuestMemberIsProvided() throws Exception {
        // when
        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.hasParameterAnnotation(AuthorizedMember.class)).thenReturn(true);
        when(loginMemberArgumentResolver.resolveArgument(parameter, mavContainer, webRequest, null)).thenReturn(guest);

        // then
        DomainException exception = assertThrows(DomainException.class, () -> authorizedMemberArgumentResolver.resolveArgument(parameter, mavContainer, webRequest, null));
        assertThat(exception.getCode()).isEqualTo(MemberErrorCode.NOT_AUTHORIZED_MEMBER.name());
    }
}
