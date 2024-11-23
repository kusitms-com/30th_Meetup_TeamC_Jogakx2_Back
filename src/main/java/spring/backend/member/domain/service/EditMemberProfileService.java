package spring.backend.member.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.dto.request.EditMemberProfileRequest;
import spring.backend.member.exception.MemberErrorCode;

@Service
@RequiredArgsConstructor
@Log4j2
public class EditMemberProfileService {

    private final MemberRepository memberRepository;

    public boolean edit(Member member, EditMemberProfileRequest editMemberProfileRequest) {
        try {
            if (validateNickname(editMemberProfileRequest.nickname())) {
                member.editMemberProfile(editMemberProfileRequest.nickname(), editMemberProfileRequest.profileImage());
                memberRepository.save(member);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("[EditMemberProfileService] Failed to edit member profile. {}", e.getMessage());
            throw MemberErrorCode.FAILED_TO_EDIT_PROFILE.toException();
        }
    }

    private boolean validateNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            log.error("[ValidateNicknameService] Nickname is empty");
            return false;
        }

        if (nickname.length() > 6) {
            log.error("[ValidateNicknameService] Nickname is smaller than 6 characters");
            return false;
        }

        if (!nickname.matches("^[a-zA-Z0-9가-힣]+$")) {
            log.error("[ValidateNicknameService] Nickname is invalid");
            return false;
        }

        return true;
    }
}

