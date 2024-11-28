package spring.backend.member.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EditMemberProfileRequest(
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,6}$", message = "닉네임은 한글, 영문, 숫자 조합 6자 이내로 입력해주세요.")
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Schema(description = "닉네임", example = "조각조각")
        String nickname,

        @NotBlank(message = "프로필 이미지를 선택해주세요.")
        @Schema(description = "프로필 이미지", example = "http://test.jpg")
        String profileImage
) {
}
