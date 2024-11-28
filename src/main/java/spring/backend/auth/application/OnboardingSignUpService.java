package spring.backend.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.auth.presentation.dto.request.OnboardingSignUpRequest;
import spring.backend.auth.presentation.dto.response.OnboardingSignUpResponse;
import spring.backend.auth.exception.AuthenticationErrorCode;
import spring.backend.member.domain.entity.Member;
import spring.backend.member.domain.repository.MemberRepository;

import java.time.Year;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OnboardingSignUpService {

    private static final int BIRTH_YEAR_RANGE = 100;

    private final MemberRepository memberRepository;

    public OnboardingSignUpResponse onboardingSignUp(Member member, OnboardingSignUpRequest request) {
        validateMember(member);
        validateRequest(request);
        validateBirthYear(request);
        member.convertGuestToMember(request.nickname(), request.birthYear(), request.gender(), request.profileImage());
        memberRepository.save(member);
        return OnboardingSignUpResponse.of(member.getEmail(), member.getNickname(), member.getBirthYear(), member.getGender(), member.getProfileImage(), member.getCreatedAt());
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
