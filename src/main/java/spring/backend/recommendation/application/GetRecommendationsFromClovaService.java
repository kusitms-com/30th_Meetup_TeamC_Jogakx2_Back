package spring.backend.recommendation.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.activity.domain.value.Type;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;
import spring.backend.recommendation.dto.response.ClovaRecommendationResponse;
import spring.backend.recommendation.infrastructure.clova.exception.ClovaErrorCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Log4j2
@RequiredArgsConstructor
public class GetRecommendationsFromClovaService {
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

    private final RecommendationProvider recommendationProvider;

    public List<ClovaRecommendationResponse> getRecommendationsFromClova(ClovaRecommendationRequest clovaRecommendationRequest) {
        validateClovaRecommendationRequestKeyword(clovaRecommendationRequest);
        String[] recommendations = recommendationProvider.requestToClovaStudio(clovaRecommendationRequest).split(LINE_SEPARATOR);

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
                    keywordCategory = convertClovaResonseKeywordToKewordCategory(keywordText);
                    i++;
                }
                clovaResponses.add(new ClovaRecommendationResponse(order, title, content, keywordCategory));
                order++;
            }
        }

        return clovaResponses;
    }

    private void validateClovaRecommendationRequestKeyword(ClovaRecommendationRequest clovaRecommendationRequest) {
        if (clovaRecommendationRequest.activityType().equals(Type.ONLINE) && Arrays.toString(clovaRecommendationRequest.keywords()).contains("NATURE")) {
            throw ClovaErrorCode.ONLINE_TYPE_CONTAIN_NATURE.toException();
        }
        if(clovaRecommendationRequest.activityType().equals(Type.OFFLINE) && Arrays.toString(clovaRecommendationRequest.keywords()).contains("SOCIAL")) {
            throw ClovaErrorCode.OFFLINE_TYPE_CONTAIN_SOCIAL.toException();
        }
    }

    private Keyword.Category convertClovaResonseKeywordToKewordCategory(String keywordText) {
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
