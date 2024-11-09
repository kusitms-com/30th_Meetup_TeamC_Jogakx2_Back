package spring.backend.recommendation.infrastructure.openai;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;
import spring.backend.core.exception.error.GlobalErrorCode;
import spring.backend.recommendation.application.RecommendationProvider;
import spring.backend.recommendation.dto.request.AIRecommendationRequest;
import spring.backend.recommendation.infrastructure.dto.Message;
import spring.backend.recommendation.infrastructure.openai.dto.request.OpenAIPrompt;
import spring.backend.recommendation.infrastructure.openai.dto.response.OpenAIResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class OpenAIRecommendationProvider implements RecommendationProvider<Mono<OpenAIResponse>> {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.secret-key}")
    private String secretKey;

    @Value("${openai.chat-completions-url}")
    private String chatCompletionsUrl;

    public Mono<OpenAIResponse> getRecommendations(AIRecommendationRequest request) {
        return WebClient.create()
                .post()
                .uri(chatCompletionsUrl)
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.setBearerAuth(secretKey);
                })
                .bodyValue(createRecommendationRequestBody(request))
                .retrieve()
                .bodyToMono(OpenAIResponse.class)
                .onErrorResume(WebClientException.class, e -> {
                    log.error("WebClient 에러 발생 - 에러 메시지: {}", e.getMessage(), e);
                    return Mono.error(GlobalErrorCode.WEB_CLIENT_ERROR.toException());
                })
                .onErrorResume(Exception.class, e -> {
                    log.error("알 수 없는 내부 오류 발생 - 에러 메시지: {}", e.getMessage(), e);
                    return Mono.error(GlobalErrorCode.INTERNAL_ERROR.toException());
                });
    }

    private Map<String, Object> createRecommendationRequestBody(AIRecommendationRequest request) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);

        List<Message> messages = new ArrayList<>();
        messages.add(Message.createSystem(OpenAIPrompt.DEFAULT_SYSTEM_PROMPT));
        messages.add(Message.createUserMessage(request));
        requestBody.put("messages", messages);
        return requestBody;
    }
}
