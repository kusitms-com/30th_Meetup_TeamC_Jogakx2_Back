package spring.backend.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthResourceResponse {

    private String id;

    private String email;

    @JsonProperty("verified_email")
    private boolean verifiedEmail;

    private String name;
}
