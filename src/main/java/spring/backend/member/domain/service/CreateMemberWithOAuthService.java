package spring.backend.member.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.presentation.dto.request.CreateMemberWithOAuthRequest;
import spring.backend.member.exception.MemberErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CreateMemberWithOAuthService {

    private final MemberRepository memberRepository;

    public Member createMemberWithOAuth(CreateMemberWithOAuthRequest request) {
        if (request == null) {
            log.error("[createMemberWithOAuth] request is null");
            throw MemberErrorCode.NOT_EXIST_CONDITION.toException();
        }

        List<Member> members = memberRepository.findAllByEmail(request.getEmail());
        if (members == null || members.isEmpty()) {
            return createGuestMember(request);
        }

        return handleExistingMember(members, request);
    }

    private Member handleExistingMember(List<Member> members, CreateMemberWithOAuthRequest request) {
        Member existingMember = members.stream()
                .filter(Member::isMember)
                .findFirst()
                .orElse(null);

        if (existingMember != null) {
            if (!existingMember.isSameProvider(request.getProvider())) {
                log.error("[CreateMemberWithOAuthService] member already exists with a different provider [{}]", existingMember.getProvider());
                throw MemberErrorCode.ALREADY_REGISTERED_WITH_DIFFERENT_OAUTH2.toException();
            }
            return existingMember;
        }

        return members.stream()
                .filter(m -> m.isSameProvider(request.getProvider()))
                .findFirst()
                .orElseGet(() -> createGuestMember(request));
    }

    private Member createGuestMember(CreateMemberWithOAuthRequest request) {
        Member newMember = Member.createGuestMember(request.getProvider(), request.getEmail());
        return saveMember(newMember);
    }

    private Member saveMember(Member member) {
        Member savedMember = memberRepository.save(member);
        if (savedMember == null) {
            log.error("[CreateMemberWithOAuthService] member could not be saved");
            throw MemberErrorCode.MEMBER_SAVE_FAILED.toException();
        }
        return savedMember;
    }
}
