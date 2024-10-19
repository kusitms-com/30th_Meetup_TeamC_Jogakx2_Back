package spring.backend.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.exception.MemberErrorCode;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findByMemberId(UUID memberId) {
        Member member = memberRepository.findById(memberId);
        return Optional.ofNullable(member).orElseThrow(MemberErrorCode.NOT_EXIST_MEMBER::toException);
    }
}
