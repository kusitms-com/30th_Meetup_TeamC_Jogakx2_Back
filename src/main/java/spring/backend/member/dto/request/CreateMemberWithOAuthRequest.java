package spring.backend.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import spring.backend.member.domain.value.Provider;

@Getter
@Builder
public class CreateMemberWithOAuthRequest {

    private final Provider provider;

    private final String email;

    private final String nickname;
}
