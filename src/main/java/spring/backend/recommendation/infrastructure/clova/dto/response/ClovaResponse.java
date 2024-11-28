package spring.backend.recommendation.infrastructure.clova.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClovaResponse {
    private Result result;

    @Getter
    @NoArgsConstructor
    public static class Result {
        private Message message;
    }

    @Getter
    @NoArgsConstructor
    public static class Message {
        private String content;
    }
}