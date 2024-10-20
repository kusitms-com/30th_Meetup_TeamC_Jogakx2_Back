package spring.backend.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import spring.backend.member.domain.value.Gender;

public record OnboardingSignUpRequest(
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,6}$", message = "닉네임은 한글, 영문, 숫자 조합 6자 이내로 입력해주세요.")
        String nickname,

        int birthYear,

        @NotNull(message = "성별을 입력해주세요.")
        Gender gender,

        @NotBlank(message = "프로필 이미지를 선택해주세요.")
        String profileImage
) {
}
