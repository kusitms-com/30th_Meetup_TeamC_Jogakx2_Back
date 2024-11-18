package spring.backend.recommendation.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import spring.backend.activity.domain.value.Keyword.Category;
import spring.backend.activity.domain.value.Type;
import spring.backend.recommendation.dto.request.AIRecommendationRequest;
import spring.backend.recommendation.dto.response.OpenAIRecommendationResponse;
import spring.backend.recommendation.infrastructure.dto.Message;
import spring.backend.recommendation.infrastructure.openai.dto.response.OpenAIResponse;
import spring.backend.recommendation.infrastructure.openai.exception.OpenAIErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class GetRecommendationsFromOpenAIService {

    private static final String LINE_SEPARATOR = "\n";
    private static final Pattern RECOMMENDATION_FIELD_PATTERN = Pattern.compile("^(title|content|keyword|platform|url):\\s*(.*)$");
    private static final int ONLINE_REQUIRED_SIZE = 5;
    private static final int ONLINE_OFFLINE_ONLINE_REQUIRED_SIZE = 2;
    private static final String TITLE_KEY = "title";
    private static final String CONTENT_KEY = "content";
    private static final String KEYWORD_KEY = "keyword";
    private static final String PLATFORM_KEY = "platform";
    private static final String URL_KEY = "url";

    private final Map<String, BiConsumer<String, String>> FIELD_SETTERS = Map.of(
            TITLE_KEY, (key, value) -> title = value,
            CONTENT_KEY, (key, value) -> content = value,
            KEYWORD_KEY, (key, value) -> keyword = value,
            PLATFORM_KEY, (key, value) -> platform = value,
            URL_KEY, (key, value) -> url = value
    );
    private String title;
    private String content;
    private String keyword;
    private String platform;
    private String url;

    private final RecommendationProvider<Mono<OpenAIResponse>> openAIRecommendationProvider;

    public Mono<List<OpenAIRecommendationResponse>> getRecommendationsFromOpenAI(AIRecommendationRequest request) {
        return openAIRecommendationProvider.getRecommendations(request)
                .flatMap(this::extractRecommendationContent)
                .switchIfEmpty(Mono.error(OpenAIErrorCode.NOT_FOUND_RECOMMENDATION.toException()))
                .flatMap(rawData -> parseRecommendations(rawData, request.activityType()))
                .flatMap(recommendations -> checkAndRetryRecommendations(recommendations, request));
    }

    private Mono<String> extractRecommendationContent(OpenAIResponse openAIResponse) {
        return Mono.justOrEmpty(openAIResponse)
                .map(OpenAIResponse::choices)
                .flatMap(choices -> Mono.justOrEmpty(choices.get(0)))
                .flatMap(choice -> Mono.justOrEmpty(choice.message()))
                .map(Message::content);
    }

    private Mono<List<OpenAIRecommendationResponse>> parseRecommendations(String rawData, Type activityType) {
        return Mono.fromCallable(() -> {
            String[] lines = rawData.split(LINE_SEPARATOR);
            List<OpenAIRecommendationResponse> recommendations = new ArrayList<>();
            int order = 1;

            for (String line : lines) {
                Matcher matcher = RECOMMENDATION_FIELD_PATTERN.matcher(line);
                if (matcher.matches()) {
                    String key = matcher.group(1);
                    String value = matcher.group(2).trim();
                    setRecommendationField(key, value);
                }

                if (isAllFieldsFilled()) {
                    Category category = Category.from(keyword);
                    if (!isValidCategory(category)) {
                        log.warn("[GetRecommendationsFromOpenAIService] Invalid Category.");
                        continue;
                    }

                    OpenAIRecommendationResponse response = OpenAIRecommendationResponse.of(order++, title, content, category, url);
                    recommendations.add(response);
                    resetRecommendationFields();
                }
            }

            return filterAndLimitRecommendations(recommendations, activityType);
        }).onErrorResume(e -> {
            log.error("[GetRecommendationsFromOpenAIService] Recommendation parsing error: {}", e.getMessage());
            return Mono.error(e);
        });
    }

    private List<OpenAIRecommendationResponse> filterAndLimitRecommendations(List<OpenAIRecommendationResponse> recommendations, Type activityType) {
        recommendations = filterRecommendations(recommendations);
        if (recommendations.isEmpty()) {
            throw OpenAIErrorCode.NOT_EXIST_CATEGORY.toException();
        }
        return limitRecommendations(recommendations, activityType);
    }

    private List<OpenAIRecommendationResponse> filterRecommendations(List<OpenAIRecommendationResponse> recommendations) {
        return recommendations.stream()
                .filter(r -> r.keywordCategory() != null)
                .collect(Collectors.toList());
    }

    private List<OpenAIRecommendationResponse> limitRecommendations(List<OpenAIRecommendationResponse> recommendations, Type activityType) {
        int requiredSize = getRequiredSize(activityType);
        return recommendations.size() > requiredSize ? recommendations.subList(0, requiredSize) : recommendations;
    }

    private Mono<List<OpenAIRecommendationResponse>> checkAndRetryRecommendations(List<OpenAIRecommendationResponse> recommendations, AIRecommendationRequest request) {
        if (isRetryRequired(recommendations, request.activityType())) {
            return retryRecommendations(request);
        }
        return Mono.just(recommendations);
    }

    private boolean isRetryRequired(List<OpenAIRecommendationResponse> recommendations, Type activityType) {
        return recommendations.isEmpty() || recommendations.size() != getRequiredSize(activityType);
    }

    private Mono<List<OpenAIRecommendationResponse>> retryRecommendations(AIRecommendationRequest request) {
        return getRecommendationsFromOpenAI(request);
    }

    private int getRequiredSize(Type activityType) {
        return switch (activityType) {
            case ONLINE -> ONLINE_REQUIRED_SIZE;
            case ONLINE_AND_OFFLINE -> ONLINE_OFFLINE_ONLINE_REQUIRED_SIZE;
            default -> 0;
        };
    }

    private boolean isAllFieldsFilled() {
        return title != null && content != null && keyword != null && platform != null && url != null;
    }

    private boolean isValidCategory(Category category) {
        return category != null;
    }

    private void setRecommendationField(String key, String value) {
        BiConsumer<String, String> fieldSetter = FIELD_SETTERS.get(key);
        if (fieldSetter != null) {
            fieldSetter.accept(key, value);
        }
    }

    private void resetRecommendationFields() {
        title = null;
        content = null;
        keyword = null;
        platform = null;
        url = null;
    }
}
