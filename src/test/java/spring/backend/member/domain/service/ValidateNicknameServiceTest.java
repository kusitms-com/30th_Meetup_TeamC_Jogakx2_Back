package spring.backend.member.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.domain.value.Role;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ValidateNicknameServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ValidateNicknameService validateNicknameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("닉네임이 공백일 때 예외가 발생한다.")
    void throwExceptionWhenNicknameIsBlank() {
        // Given
        String nickname = " ";

        // When & Then
        assertFalse(validateNicknameService.validateNickname(nickname));
    }

    @Test
    @DisplayName("닉네임 길이가 6자를 초과할 때 예외가 발생한다.")
    void throwExceptionWhenNicknameLengthIsInvalid() {
        // Given
        String nickname = "1234567";

        // When & Then
        assertFalse(validateNicknameService.validateNickname(nickname));
    }

    @Test
    @DisplayName("닉네임 형식이 유효하지 않을 때 예외가 발생한다.")
    void throwExceptionWhenNicknameFormatIsInvalid() {
        // Given
        String nickname = "조각ㅈㄱ";

        // When & Then
        assertFalse(validateNicknameService.validateNickname(nickname));
    }

    @Test
    @DisplayName("이미 등록된 닉네임일 경우 예외가 발생한다.")
    void throwExceptionWhenNicknameIsAlreadyRegistered() {
        // Given
        String nickname = "등록된이름";
        when(memberRepository.existsByNicknameAndRole(nickname, Role.MEMBER)).thenReturn(true);

        // When & Then
        assertFalse(validateNicknameService.validateNickname(nickname));

        // Mock 객체 정상 동작 확인
        verify(memberRepository).existsByNicknameAndRole(nickname, Role.MEMBER);
    }
}
