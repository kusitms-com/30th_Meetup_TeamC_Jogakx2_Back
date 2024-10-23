package spring.backend.auth.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.backend.auth.dto.request.OnboardingSignUpRequest;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.core.exception.DomainException;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.domain.value.Gender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OnboardingSignUpServiceTest {

    @InjectMocks
    private OnboardingSignUpService onboardingSignUpService;

    @Mock
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        member = mock(Member.class);
    }

    @DisplayName("회원가입 요청이 null인 경우 예외가 발생한다")
    @Test
    public void throwExceptionWhenRequestIsNull() {
        // When & Then
        Exception exception = assertThrows(DomainException.class,
                () -> onboardingSignUpService.onboardingSignUp(member, null));
        assertEquals(AuthenticationErrorCode.NOT_EXIST_SIGN_UP_CONDITION.getMessage(), exception.getMessage());
    }

    @DisplayName("유효하지 않은 멤버 상태인 경우 예외가 발생한다")
    @Test
    public void throwExceptionWhenMemberIsInvalid() {
        // Given
        when(member.isMember()).thenReturn(true);

        // When & Then
        Exception exception = assertThrows(DomainException.class,
                () -> onboardingSignUpService.onboardingSignUp(member, new OnboardingSignUpRequest("조각조각", 2000, Gender.MALE, "http://test.jpg")));
        assertEquals(AuthenticationErrorCode.INVALID_MEMBER_SIGN_UP_CONDITION.getMessage(), exception.getMessage());
    }

    @DisplayName("출생년도가 유효하지 않은 경우 예외가 발생한다")
    @Test
    public void throwExceptionWhenBirthYearIsInvalid() {
        // Given
        OnboardingSignUpRequest request = new OnboardingSignUpRequest("조각조각", 1900, Gender.MALE, "http://test.jpg");

        // When & Then
        Exception exception = assertThrows(DomainException.class,
                () -> onboardingSignUpService.onboardingSignUp(member, request));

        assertEquals(AuthenticationErrorCode.INVALID_BIRTH_YEAR.getMessage(), exception.getMessage());
    }

    @DisplayName("유효한 회원가입 요청인 경우 회원 정보가 저장된다")
    @Test
    public void saveMemberWhenRequestIsValid() {
        // Given
        OnboardingSignUpRequest request = new OnboardingSignUpRequest("조각조각", 2001, Gender.MALE, "http://test.jpg");
        when(member.isMember()).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // When
        Member result = onboardingSignUpService.onboardingSignUp(member, request);

        // Then
        assertNotNull(result);
        verify(member).convertGuestToMember("조각조각", 2001, Gender.MALE, "http://test.jpg");
        verify(memberRepository).save(member);
    }
}