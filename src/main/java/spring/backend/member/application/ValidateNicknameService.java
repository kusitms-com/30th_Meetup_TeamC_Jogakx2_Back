package spring.backend.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.backend.member.domain.repository.MemberRepository;
import spring.backend.member.domain.value.Role;
import spring.backend.member.exception.MemberErrorCode;

@Service
@RequiredArgsConstructor
@Log4j2
public class ValidateNicknameService {

    private final MemberRepository memberRepository;

    public boolean validateNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            log.error("[ValidateNicknameService] Nickname is empty");
            throw MemberErrorCode.NOT_EXIST_NICKNAME.toException();
        }
        if (nickname.length() > 6) {
            log.error("[ValidateNicknameService] Nickname is smaller than 6 characters");
            throw MemberErrorCode.INVALID_NICKNAME_LENGTH.toException();
        }
        if (!nickname.matches("^[a-zA-Z0-9가-힣]+$")) {
            log.error("[ValidateNicknameService] Nickname is invalid");
            throw MemberErrorCode.INVALID_NICKNAME_FORMAT.toException();
        }
        if (memberRepository.existsByNicknameAndRole(nickname, Role.MEMBER)) {
            throw MemberErrorCode.ALREADY_REGISTERED_NICKNAME.toException();
        }
        return true;
    }
}
