package spring.backend.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.domain.value.Role;
import spring.backend.member.dto.request.CreateMemberWithOAuthRequest;
import spring.backend.member.exception.MemberErrorCode;

@Service
@RequiredArgsConstructor
@Log4j2
public class CreateMemberWithOAuthService {

    private final MemberRepository memberRepository;

    public Member createMemberWithOAuth(CreateMemberWithOAuthRequest request) {
        if (request == null) {
            log.error("[createMemberWithOAuth] request is null");
            throw MemberErrorCode.NOT_EXIST_CONDITION.toException();
        }
        Member member = memberRepository.findByEmail(request.getEmail());
        if (member == null) {
            Member newMember = Member.builder()
                    .provider(request.getProvider())
                    .role(Role.GUEST)
                    .email(request.getEmail())
                    .nickname(request.getNickname())
                    .build();
            memberRepository.save(newMember);
            return newMember;
        }
        if (!member.isSameProvider(request.getProvider())) {
            log.error("[createMemberWithOAuth] provider mismatch");
            throw MemberErrorCode.ALREADY_EXIST_EMAIL.toException();
        }
        return member;
    }
}
