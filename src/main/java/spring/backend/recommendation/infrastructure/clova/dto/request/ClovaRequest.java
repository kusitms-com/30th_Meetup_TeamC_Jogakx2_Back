package spring.backend.recommendation.infrastructure.clova.dto.request;

import lombok.Builder;
import lombok.Getter;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;

import java.util.ArrayList;

@Builder
@Getter
public class ClovaRequest {
    private ArrayList<Message> messages;
    private double topP;
    private int topK;
    private int maxTokens;
    private double temperature;
    private double repeatPenalty;
    private boolean includeAiFilters;
    private int seed;

    public static ClovaRequest createClovaRequest(ClovaRecommendationRequest userInputRequest) {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(Message.createSystem());
        messages.add(Message.createMessage(userInputRequest));

        return ClovaRequest.builder()
                .messages(messages)
                .topP(0.8)
                .topK(0)
                .maxTokens(500)
                .temperature(0.5)
                .repeatPenalty(5.0)
                .includeAiFilters(true)
                .seed(0)
                .build();
    }
}
