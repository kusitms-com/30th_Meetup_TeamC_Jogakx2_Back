package spring.backend.auth.infrastructure.naver.dto;

import lombok.Getter;

@Getter
public class NaverResourceResponse {

    private Response response;

    @Getter
    public static class Response {
        private String id;
        private String name;
        private String email;
    }
}
