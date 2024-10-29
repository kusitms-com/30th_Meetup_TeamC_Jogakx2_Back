package spring.backend.recommendation.infrastructure.clova.dto.request;

import lombok.Builder;
import lombok.Getter;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;

import java.util.ArrayList;

@Builder
@Getter
public class ClovaRequest {
    private static final double DEFAULT_TOP_P = 0.8;
    private static final int DEFAULT_TOP_K = 0;
    private static final int DEFAULT_MAX_TOKENS = 500;
    private static final double DEFAULT_TEMPERATURE = 0.5;
    private static final double DEFAULT_REPEAT_PENALTY = 5.0;
    private static final boolean DEFAULT_INCLUDE_AI_FILTERS = true;
    private static final int DEFAULT_SEED = 0;

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
                .topP(DEFAULT_TOP_P)
                .topK(DEFAULT_TOP_K)
                .maxTokens(DEFAULT_MAX_TOKENS)
                .temperature(DEFAULT_TEMPERATURE)
                .repeatPenalty(DEFAULT_REPEAT_PENALTY)
                .includeAiFilters(DEFAULT_INCLUDE_AI_FILTERS)
                .seed(DEFAULT_SEED)
                .build();
    }
}
