package spring.backend.member.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.backend.member.application.ValidateNicknameService;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.dto.request.EditMemberProfileRequest;
import spring.backend.member.exception.MemberErrorCode;

@Service
@RequiredArgsConstructor
@Log4j2
public class EditMemberProfileService {

    private final MemberRepository memberRepository;
    private final ValidateNicknameService validateNicknameService;

    public boolean edit(Member member, EditMemberProfileRequest editMemberProfileRequest) {
        validateNicknameService.validateNickname(editMemberProfileRequest.nickname());
        member.editMemberProfile(editMemberProfileRequest.nickname(), editMemberProfileRequest.profileImage());
        memberRepository.save(member);
        return true;
    }
}
