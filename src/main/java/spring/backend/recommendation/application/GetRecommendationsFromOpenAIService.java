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
import spring.backend.recommendation.infrastructure.openai.dto.response.OpenAIResponse.Choice;
import spring.backend.recommendation.infrastructure.openai.exception.OpenAIErrorCode;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class GetRecommendationsFromOpenAIService {

    private static final String LINE_SEPARATOR = "\n";
    private static final Pattern RECOMMENDATION_FIELD_PATTERN = Pattern.compile("^(title|content|keyword|platform|url):\\s*(.*)$");
    private static final Pattern LINK_SEARCH_QUERY_PATTERN = Pattern.compile("'(.*?)'");
    private static final String TITLE_KEY = "title";
    private static final String CONTENT_KEY = "content";
    private static final String KEYWORD_KEY = "keyword";
    private static final String PLATFORM_KEY = "platform";
    private static final String URL_KEY = "url";
    private static final String YOUTUBE_PLATFORM = "youtube";
    private static final int ONLINE_REQUIRED_SIZE = 5;
    private static final int ONLINE_OFFLINE_OPENAI_REQUIRED_SIZE = 2;
    private static final int MAX_RETRY_ATTEMPTS = 2;

    private final RecommendationProvider<Mono<OpenAIResponse>> openAIRecommendationProvider;
    private final SearchYouTubeService searchYouTubeService;

    public List<OpenAIRecommendationResponse> getRecommendationsFromOpenAI(AIRecommendationRequest request) {
        for (int attempt = 1; attempt <= MAX_RETRY_ATTEMPTS; attempt++) {
            try {
                OpenAIResponse openAIResponse = openAIRecommendationProvider.getRecommendations(request).block();
                if (openAIResponse == null) {
                    throw OpenAIErrorCode.NOT_FOUND_RECOMMENDATION.toException();
                }

                String rawData = extractRecommendationContent(openAIResponse);
                List<OpenAIRecommendationResponse> recommendations = parseRecommendations(rawData, request.activityType());

                if (attempt == 1 && (recommendations.isEmpty() || isKeywordMissing(recommendations))) {
                    log.warn("[GetRecommendationsFromOpenAIService] First attempt failed : {}", recommendations);
                    continue;
                }

                return recommendations;

            } catch (Exception e) {
                if (attempt == MAX_RETRY_ATTEMPTS) {
                    log.error("[GetRecommendationsFromOpenAIService] Maximum retry attempts exceeded.", e);
                    throw OpenAIErrorCode.FAILED_RECOMMENDATION_GENERATION.toException();
                }
                log.warn("[GetRecommendationsFromOpenAIService] Invalid response received. Retrying attempt: {}", attempt + 1, e);
            }
        }
        throw OpenAIErrorCode.FAILED_RECOMMENDATION_GENERATION.toException();
    }

    private String extractRecommendationContent(OpenAIResponse openAIResponse) {
        return Optional.ofNullable(openAIResponse)
                .map(OpenAIResponse::choices)
                .map(choices -> choices.get(0))
                .map(Choice::message)
                .map(Message::content)
                .orElse(null);
    }

    private List<OpenAIRecommendationResponse> parseRecommendations(String rawData, Type activityType) {
        if (rawData == null || rawData.isEmpty()) {
            throw OpenAIErrorCode.NOT_FOUND_RECOMMENDATION.toException();
        }

        String[] lines = rawData.split(LINE_SEPARATOR);
        List<OpenAIRecommendationResponse> recommendations = new ArrayList<>();
        int order = 1;

        Map<String, String> recommendationFields = new HashMap<>();

        for (String line : lines) {
            Matcher matcher = RECOMMENDATION_FIELD_PATTERN.matcher(line);

            if (matcher.matches()) {
                recommendationFields.put(matcher.group(1).toLowerCase(), matcher.group(2).trim());
            }

            if (hasAllRequiredFields(recommendationFields)) {
                String keyword = recommendationFields.get(KEYWORD_KEY);
                Category category = Category.from(keyword);

                if (isInvalidCategory(category)) {
                    log.warn("[GetRecommendationsFromOpenAIService] Invalid Category.");
                    recommendationFields.clear();
                    continue;
                }

                String title = recommendationFields.get(TITLE_KEY);
                String platform = recommendationFields.get(PLATFORM_KEY);
                String url = recommendationFields.get(URL_KEY);
                String youtubeUrl = processYoutubeUrl(title, platform, url);

                recommendations.add(OpenAIRecommendationResponse.of(
                        order++,
                        title,
                        recommendationFields.get(CONTENT_KEY),
                        category,
                        youtubeUrl
                ));

                recommendationFields.clear();
            }
        }

        return filterAndLimitRecommendations(recommendations, activityType);
    }

    private List<OpenAIRecommendationResponse> filterAndLimitRecommendations(List<OpenAIRecommendationResponse> recommendations, Type activityType) {
        return recommendations.stream()
                .filter(r -> r.keywordCategory() != null)
                .limit(getRequiredSize(activityType))
                .collect(Collectors.toList());
    }

    private int getRequiredSize(Type activityType) {
        return switch (activityType) {
            case ONLINE -> ONLINE_REQUIRED_SIZE;
            case ONLINE_AND_OFFLINE -> ONLINE_OFFLINE_OPENAI_REQUIRED_SIZE;
            default -> 0;
        };
    }

    private boolean isInvalidCategory(Category category) {
        return category == null;
    }

    private boolean isKeywordMissing(List<OpenAIRecommendationResponse> recommendations) {
        return recommendations.stream()
                .anyMatch(r -> isInvalidCategory(r.keywordCategory()));
    }

    private boolean hasAllRequiredFields(Map<String, String> recommendationFields) {
        return recommendationFields.containsKey(TITLE_KEY)
                && recommendationFields.containsKey(CONTENT_KEY)
                && recommendationFields.containsKey(KEYWORD_KEY)
                && recommendationFields.containsKey(PLATFORM_KEY)
                && recommendationFields.containsKey(URL_KEY);
    }

    private String processYoutubeUrl(String title, String platform, String url) {
        if (YOUTUBE_PLATFORM.equalsIgnoreCase(platform)) {
            String youtubeUrl = searchYouTubeService.searchYoutube(extractYoutubeUrlFromTitle(title));
            if (youtubeUrl != null) {
                return youtubeUrl;
            }
        }
        return url;
    }

    private String extractYoutubeUrlFromTitle(String title) {
        Matcher matcher = LINK_SEARCH_QUERY_PATTERN.matcher(title);
        return matcher.find() ? matcher.group(1) : null;
    }
}
