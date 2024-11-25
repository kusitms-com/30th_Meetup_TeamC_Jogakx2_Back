package spring.backend.member.dto.response;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.backend.member.domain.entity.Member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MemberProfileResponseTest {

    @DisplayName("from 메서드가 올바르게 작동하는지 확인한다")
    @Test
    void testFromMethodInMemberProfileResponse() {
        // given
        Member member = Member.builder()
                .email("jogakjogak@gmail.com")
                .emailNotification(true)
                .build();

        // when
        MemberProfileResponse response = MemberProfileResponse.from(member);

        // then
        assertThat(response.email()).isEqualTo("jogakjogak@gmail.com");
        assertThat(response.isEmailNotificationEnabled()).isTrue();
    }
}
