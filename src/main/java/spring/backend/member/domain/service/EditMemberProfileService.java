package spring.backend.member.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.presentation.dto.request.EditMemberProfileRequest;

@Service
@RequiredArgsConstructor
@Log4j2
public class EditMemberProfileService {

    private final MemberRepository memberRepository;

    @Transactional
    public void edit(Member member, EditMemberProfileRequest editMemberProfileRequest) {
        member.editMemberProfile(editMemberProfileRequest.nickname(), editMemberProfileRequest.profileImage());
        memberRepository.save(member);
    }
}
