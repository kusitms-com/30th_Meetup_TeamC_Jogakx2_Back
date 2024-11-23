package spring.backend.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import spring.backend.member.domain.value.Gender;

import java.time.LocalDateTime;

public record OnboardingSignUpResponse(

        @Schema(description = "사용자 이메일", example = "example@example.com")
        String email,

        @Schema(description = "사용자 닉네임", example = "john_doe")
        String nickname,

        @Schema(description = "사용자 출생 연도", example = "1990")
        int birthYear,

        @Schema(description = "사용자 성별 (MALE, FEMALE, NONE)", example = "MALE")
        Gender gender,

        @Schema(description = "사용자 프로필 이미지 URL", example = "https://example.com/profile.jpg")
        String profileImage,

        @Schema(description = "사용자 가입 날짜", example = "2024-11-14T06:10:55.091954")
        LocalDateTime registrationDate
){
    public static OnboardingSignUpResponse of(String email, String nickname, int birthYear, Gender gender, String profileImage, LocalDateTime registrationDate) {
        return new OnboardingSignUpResponse(email, nickname, birthYear, gender, profileImage, registrationDate);
    }
}
