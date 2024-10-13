package spring.backend.auth.infrastructure.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoResourceResponse {

    private Long id;

    @JsonProperty("kakao_account")
    private Response kakaoAccount;

    @Getter
    public static class Response {

        private String email;

        private Profile profile;

        @Getter
        public static class Profile {
            private String nickname;
        }
    }
}
