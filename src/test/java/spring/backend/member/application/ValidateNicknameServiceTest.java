package spring.backend.member.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.backend.core.exception.DomainException;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.domain.value.Role;
import spring.backend.member.exception.MemberErrorCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        // When
        DomainException blankException = assertThrows(DomainException.class, () -> validateNicknameService.validateNickname(nickname));

        // Then
        assertEquals(MemberErrorCode.NOT_EXIST_NICKNAME.name(), blankException.getCode());
    }

    @Test
    @DisplayName("닉네임 길이가 6자를 초과할 때 예외가 발생한다.")
    void throwExceptionWhenNicknameLengthIsInvalid() {
        // Given
        String nickname = "1234567";

        // When
        DomainException longLengthException = assertThrows(DomainException.class, () -> validateNicknameService.validateNickname(nickname));

        // Then
        assertEquals(MemberErrorCode.INVALID_NICKNAME_LENGTH.name(), longLengthException.getCode());
    }

    @Test
    @DisplayName("닉네임 형식이 유효하지 않을 때 예외가 발생한다.")
    void throwExceptionWhenNicknameFormatIsInvalid() {
        // Given
        String nickname = "조각ㅈㄱ";

        // When
        DomainException formatException = assertThrows(DomainException.class, () -> validateNicknameService.validateNickname(nickname));

        // Then
        assertEquals(MemberErrorCode.INVALID_NICKNAME_FORMAT.name(), formatException.getCode());
    }

    @Test
    @DisplayName("이미 등록된 닉네임일 경우 예외가 발생한다.")
    void throwExceptionWhenNicknameIsAlreadyRegistered() {
        // Given
        String nickname = "등록된이름";
        when(memberRepository.existsByNicknameAndRole(nickname, Role.MEMBER)).thenReturn(true);

        // When
        DomainException alreadyRegisteredException = assertThrows(DomainException.class, () -> validateNicknameService.validateNickname(nickname));

        // Then
        assertEquals(MemberErrorCode.ALREADY_REGISTERED_NICKNAME.name(), alreadyRegisteredException.getCode());

        // Mock 객체 정상 동작 확인
        verify(memberRepository).existsByNicknameAndRole(nickname, Role.MEMBER);
    }
}