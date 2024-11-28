package spring.backend.member.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.backend.core.exception.DomainException;
import spring.backend.member.exception.MemberErrorCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberTest {

    @DisplayName("이메일 알림이 이미 활성화된 상태에서 다시 활성화하려고 하면 예외가 발생한다.")
    @Test
    void changeEmailNotification_throwsException_whenAlreadyEnabled() {
        // Given
        Member member = Member.builder()
                .emailNotification(true)
                .build();

        // Then
        DomainException ex = assertThrows(DomainException.class, () -> member.changeEmailNotification(true));
        assertEquals(MemberErrorCode.ALREADY_ENABLE_EMAIL_NOTIFICATION.getMessage(), ex.getMessage());
    }

    @DisplayName("이메일 알림이 이미 비활성화된 상태에서 다시 비활성화하려고 하면 예외가 발생한다.")
    @Test
    void changeEmailNotification_throwsException_whenAlreadyDisabled() {
        // Given
        Member member = Member.builder()
                .emailNotification(false)
                .build();

        // Then
        DomainException ex = assertThrows(DomainException.class, () -> member.changeEmailNotification(false));
        assertEquals(MemberErrorCode.ALREADY_DISABLE_EMAIL_NOTIFICATION.getMessage(), ex.getMessage());
    }
}
