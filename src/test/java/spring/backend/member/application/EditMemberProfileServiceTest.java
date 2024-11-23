package spring.backend.member.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import spring.backend.core.exception.DomainException;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.service.EditMemberProfileService;
import spring.backend.member.domain.value.Role;
import spring.backend.member.dto.request.EditMemberProfileRequest;
import spring.backend.member.exception.MemberErrorCode;

import static org.junit.jupiter.api.Assertions.*;

public class EditMemberProfileServiceTest {
    @InjectMocks
    private EditMemberProfileService editMemberProfileService;

    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .role(Role.MEMBER)
                .build();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("닉네임이 공백일 때 예외가 발생한다.")
    void throwExceptionWhenNicknameIsBlank() {
        // Given
        String nickname = " ";
        EditMemberProfileRequest editMemberProfileRequest = new EditMemberProfileRequest(nickname, "profileImage");

        // When & Then
        assertFalse(editMemberProfileService.edit(member, editMemberProfileRequest));
    }

    @Test
    @DisplayName("닉네임 길이가 6자를 초과할 때 예외가 발생한다.")
    void throwExceptionWhenNicknameLengthIsInvalid() {
        // Given
        String nickname = "1234567";
        EditMemberProfileRequest editMemberProfileRequest = new EditMemberProfileRequest(nickname, "profileImage");

        // When & Then
        assertFalse(editMemberProfileService.edit(member, editMemberProfileRequest));
    }

    @Test
    @DisplayName("닉네임 형식이 유효하지 않을 때 예외가 발생한다.")
    void throwExceptionWhenNicknameFormatIsInvalid() {
        // Given
        String nickname = "조각ㅈㄱ";
        EditMemberProfileRequest editMemberProfileRequest = new EditMemberProfileRequest(nickname, "profileImage");

        // When & Then
        assertFalse(editMemberProfileService.edit(member, editMemberProfileRequest));
    }
}
