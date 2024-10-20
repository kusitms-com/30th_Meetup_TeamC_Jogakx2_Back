package spring.backend.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.auth.dto.request.OnboardingSignUpRequest;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.member.application.MemberServiceHelper;
import spring.backend.member.domain.entity.Member;

import java.time.Year;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OnboardingSignUpService {

    private static final int BIRTH_YEAR_RANGE = 100;

    private final MemberServiceHelper memberServiceHelper;

    public Member onboardingSignUp(Member member, OnboardingSignUpRequest request) {
        validateMember(member);
        validateRequest(request);
        validateBirthYear(request);
        member.convertGuestToMember(request.nickname(), request.birthYear(), request.gender(), request.profileImage());
        return memberServiceHelper.save(member);
    }

    private void validateMember(Member member) {
        if (member == null || member.isMember()) {
            log.error("[OnboardingSignUpService] Invalid member condition for sign-up.");
            throw AuthenticationErrorCode.INVALID_MEMBER_SIGN_UP_CONDITION.toException();
        }
    }

    private void validateRequest(OnboardingSignUpRequest request) {
        if (request == null) {
            log.error("[OnboardingSignUpService] Invalid request.");
            throw AuthenticationErrorCode.NOT_EXIST_SIGN_UP_CONDITION.toException();
        }
    }

    private void validateBirthYear(OnboardingSignUpRequest request) {
        int birthYear = request.birthYear();
        int currentYear = Year.now().getValue();
        if (birthYear < (currentYear - BIRTH_YEAR_RANGE) || birthYear > currentYear) {
            log.error("[OnboardingSignUpService] Invalid request birth year.");
            throw AuthenticationErrorCode.INVALID_BIRTH_YEAR.toException();
        }
    }
}
