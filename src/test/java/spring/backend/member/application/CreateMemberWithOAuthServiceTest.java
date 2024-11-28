package spring.backend.member.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.domain.value.Provider;
import spring.backend.member.domain.value.Role;
import spring.backend.member.presentation.dto.request.CreateMemberWithOAuthRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateMemberWithOAuthServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CreateMemberWithOAuthService createMemberWithOAuthService;

    @DisplayName("Role이 GUEST인 경우 닉네임은 null이어야 한다")
    @Test
    void createGuestMember_returnsGuestMemberWithoutNickname() {
        // given
        CreateMemberWithOAuthRequest request = CreateMemberWithOAuthRequest.builder()
                .provider(Provider.GOOGLE)
                .email("jogakjogak@gmail.com")
                .build();

        // when
        Member newMember = Member.createGuestMember(request.getProvider(), request.getEmail());
        when(memberRepository.save(any(Member.class))).thenReturn(newMember);
        Member result = createMemberWithOAuthService.createMemberWithOAuth(request);

        // then
        assertNotNull(result);
        assertEquals(Provider.GOOGLE, result.getProvider());
        assertEquals("jogakjogak@gmail.com", result.getEmail());
        assertEquals(Role.GUEST, result.getRole());
        assertNull(result.getNickname());
        verify(memberRepository, times(1)).save(any(Member.class));
    }
}
