package spring.backend.recommendation.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.exception.ActivityErrorCode;
import spring.backend.recommendation.dto.request.AIRecommendationRequest;
import spring.backend.recommendation.dto.response.ClovaRecommendationResponse;
import spring.backend.recommendation.infrastructure.clova.dto.response.ClovaResponse;
import spring.backend.recommendation.infrastructure.clova.exception.ClovaErrorCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static spring.backend.activity.domain.value.Type.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class GetRecommendationsFromClovaService {
    private static final int MAX_ATTEMPTS = 1;

    private static final Pattern TITLE_FULL_LINE_PATTERN = Pattern.compile(".*title :.*");
    private static final Pattern TITLE_PREFIX_PATTERN = Pattern.compile(".*title :");
    private static final Pattern CONTENT_PREFIX_PATTERN = Pattern.compile(".*content :");
    private static final Pattern KEYWORD_PREFIX_PATTERN = Pattern.compile(".*keyword :");
    private static final String LINE_SEPARATOR = "\n";
    private static final String SELF_DEVELOPMENT = "자기개발";
    private static final String HEALTH = "건강";
    private static final String NATURE = "자연";
    private static final String CULTURE_ART = "문화/예술";
    private static final String ENTERTAINMENT = "엔터테인먼트";
    private static final String RELAXATION = "휴식";
    private static final String SOCIAL = "소셜";

    private final RecommendationProvider<ClovaResponse> recommendationProvider;

    public List<ClovaRecommendationResponse> getRecommendationsFromClova(AIRecommendationRequest aiRecommendationRequest) {
        validateLocation(aiRecommendationRequest);
        List<ClovaRecommendationResponse> clovaResponses = fetchRecommendations(aiRecommendationRequest);
        int attempt = 1;

        while (containsInvalidKeyword(clovaResponses) && attempt <= MAX_ATTEMPTS) {
            log.warn("추천활동의 키워드가 올바르지 않습니다. 재시도 횟수: {}/{}", attempt, MAX_ATTEMPTS);
            clovaResponses = fetchRecommendations(aiRecommendationRequest);
            attempt++;
        }

        List<ClovaRecommendationResponse> validRecommendations = filteredValidRecommendations(clovaResponses);

        if (validRecommendations.isEmpty()) {
            throw ClovaErrorCode.INVALID_KEYWORD_IN_RECOMMENDATIONS.toException();
        }

        return validRecommendations;
    }

    List<ClovaRecommendationResponse> filteredValidRecommendations(List<ClovaRecommendationResponse> clovaResponses) {
        return clovaResponses.stream()
                .filter(clovaResponse -> clovaResponse.getKeywordCategory() != null && isValidKeywordCategory(clovaResponse.getKeywordCategory())).collect(Collectors.toList());
    }

    public List<ClovaRecommendationResponse> fetchRecommendations(AIRecommendationRequest aiRecommendationRequest) {
        validateClovaRecommendationRequestKeyword(aiRecommendationRequest);
        ClovaResponse clovaResponse = recommendationProvider.getRecommendations(aiRecommendationRequest);
        validateClovaResponse(clovaResponse);
        String parsedClovaResponse = clovaResponse.getResult().getMessage().getContent();

        String[] recommendations = parsedClovaResponse.split(LINE_SEPARATOR);

        List<ClovaRecommendationResponse> clovaResponses = new ArrayList<>();
        int order = 1;

        for (int i = 0; i < recommendations.length; i++) {
            String line = recommendations[i].trim();

            if (TITLE_FULL_LINE_PATTERN.matcher(line).matches()) {
                String title = TITLE_PREFIX_PATTERN.matcher(line).replaceFirst("").trim();
                if (i + 1 < recommendations.length && recommendations[i + 1].trim().startsWith("http")) {
                    title += " " + recommendations[i + 1].trim();
                    i++;
                }

                String content = "";
                if (i + 1 < recommendations.length && CONTENT_PREFIX_PATTERN.matcher(recommendations[i + 1].trim()).find()) {
                    content = CONTENT_PREFIX_PATTERN.matcher(recommendations[i + 1].trim()).replaceFirst("").trim();
                    i++;
                }

                Keyword.Category keywordCategory = null;
                if (i + 1 < recommendations.length && KEYWORD_PREFIX_PATTERN.matcher(recommendations[i + 1].trim()).find()) {
                    String keywordText = KEYWORD_PREFIX_PATTERN.matcher(recommendations[i + 1].trim()).replaceFirst("").trim();
                    keywordCategory = convertClovaResponseKeywordToKeywordCategory(keywordText);
                    i++;
                }
                clovaResponses.add(new ClovaRecommendationResponse(order, title, content, keywordCategory));
                order++;
            }
        }

        return clovaResponses;
    }

    private void validateLocation(AIRecommendationRequest aiRecommendationRequest) {
        if ((aiRecommendationRequest.activityType() == OFFLINE || aiRecommendationRequest.activityType() == ONLINE_AND_OFFLINE) &&
                (aiRecommendationRequest.location() == null || aiRecommendationRequest.location().isEmpty())) {
            log.error("[AIRecommendationRequest] location must exist when activityType is OFFLINE or ONLINE_AND_OFFLINE");
            throw ActivityErrorCode.NOT_EXIST_LOCATION_WHEN_OFFLINE.toException();
        }

        if (aiRecommendationRequest.activityType() == ONLINE && aiRecommendationRequest.location() != null && !aiRecommendationRequest.location().isEmpty()) {
            log.error("[AIRecommendationRequest] location must not exist when activityType is ONLINE");
            throw ActivityErrorCode.EXIST_LOCATION_WHEN_ONLINE.toException();
        }
    }

    private void validateClovaResponse(ClovaResponse clovaResponse) {
        if (clovaResponse == null || clovaResponse.getResult() == null || clovaResponse.getResult().getMessage() == null || clovaResponse.getResult().getMessage().getContent() == null) {
            log.error("Clova 서비스로부터 null 응답을 수신했습니다.");
            throw ClovaErrorCode.NULL_RESPONSE_FROM_CLOVA.toException();
        }

    }

    private boolean containsInvalidKeyword(List<ClovaRecommendationResponse> clovaResponses) {
        return clovaResponses.stream().anyMatch(clovaResponse ->
                clovaResponse.getKeywordCategory() == null
                        || !isValidKeywordCategory(clovaResponse.getKeywordCategory()));
    }

    private boolean isValidKeywordCategory(Keyword.Category keywordCategory) {
        return Arrays.stream(Keyword.Category.values()).anyMatch(category -> category == keywordCategory);
    }

    private void validateClovaRecommendationRequestKeyword(AIRecommendationRequest clovaRecommendationRequest) {
        if (clovaRecommendationRequest.activityType().equals(ONLINE) && Arrays.asList(clovaRecommendationRequest.keywords()).contains(Keyword.Category.NATURE)
        ) {
            throw ClovaErrorCode.ONLINE_TYPE_CONTAIN_NATURE.toException();
        }
        if (clovaRecommendationRequest.activityType().equals(OFFLINE) && Arrays.asList(clovaRecommendationRequest.keywords()).contains(Keyword.Category.SOCIAL)
        ) {
            throw ClovaErrorCode.OFFLINE_TYPE_CONTAIN_SOCIAL.toException();
        }
    }

    private Keyword.Category convertClovaResponseKeywordToKeywordCategory(String keywordText) {
        return switch (keywordText) {
            case SELF_DEVELOPMENT -> Keyword.Category.SELF_DEVELOPMENT;
            case HEALTH -> Keyword.Category.HEALTH;
            case NATURE -> Keyword.Category.NATURE;
            case CULTURE_ART -> Keyword.Category.CULTURE_ART;
            case ENTERTAINMENT -> Keyword.Category.ENTERTAINMENT;
            case RELAXATION -> Keyword.Category.RELAXATION;
            case SOCIAL -> Keyword.Category.SOCIAL;
            default -> null;
        };
    }
}
