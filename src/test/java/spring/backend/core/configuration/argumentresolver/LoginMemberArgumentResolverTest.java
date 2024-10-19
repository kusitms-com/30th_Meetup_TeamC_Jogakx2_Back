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
import spring.backend.core.application.JwtService;
import spring.backend.member.application.MemberService;
import spring.backend.member.domain.entity.Member;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginMemberArgumentResolverTest {

    @InjectMocks
    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    @Mock
    private JwtService jwtService;

    @Mock
    private MemberService memberService;

    @Mock
    private NativeWebRequest webRequest;

    @Mock
    private ModelAndViewContainer mavContainer;

    private UUID memberId;
    private String token;
    private Member member;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        memberId = UUID.randomUUID();
        member = Member.builder()
                .id(memberId)
                .build();
        token = jwtService.provideAccessToken(member);
    }

    @DisplayName("LoginMember 어노테이션이 있는 경우 지원한다")
    @Test
    public void supportsParameterReturnsTrueForLoginMember() {
        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.hasParameterAnnotation(LoginMember.class)).thenReturn(true);
        Assertions.assertTrue(loginMemberArgumentResolver.supportsParameter(parameter));
    }

    @DisplayName("Authorization 헤더에 유효한 토큰이 있을 때 Member 객체를 반환한다")
    @Test
    public void returnsMemberObject_whenAuthorizationHeaderIsProvided() throws Exception {
        // when
        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.hasParameterAnnotation(LoginMember.class)).thenReturn(true);
        when(webRequest.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractMemberId(any(String.class))).thenReturn(memberId);
        when(memberService.findByMemberId(memberId)).thenReturn(member);

        // then
        Object result = loginMemberArgumentResolver.resolveArgument(parameter, mavContainer, webRequest, null);
        assertNotNull(result);
        assertThat(result).isEqualTo(member);
    }
}
