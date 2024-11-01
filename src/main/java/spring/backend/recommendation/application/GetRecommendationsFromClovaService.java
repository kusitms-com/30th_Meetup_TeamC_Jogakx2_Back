package spring.backend.recommendation.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.backend.activity.domain.value.Keyword;
import spring.backend.recommendation.dto.request.ClovaRecommendationRequest;
import spring.backend.recommendation.dto.response.ClovaRecommendationResponse;

import java.util.ArrayList;
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
    private final RecommendationProvider recommendationProvider;

    public List<ClovaRecommendationResponse> getRecommendationsFromClova(ClovaRecommendationRequest clovaRecommendationRequest) {
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

    private Keyword.Category convertClovaResonseKeywordToKewordCategory(String keywordText) {
        switch (keywordText.toLowerCase()) {
            case "자기개발":
                return Keyword.Category.SELF_DEVELOPMENT;
            case "건강":
                return Keyword.Category.HEALTH;
            case "자연":
                return Keyword.Category.NATURE;
            case "문화/예술":
                return Keyword.Category.CULTURE_ART;
            case "엔터테인먼트":
                return Keyword.Category.ENTERTAINMENT;
            case "휴식":
                return Keyword.Category.RELAXATION;
            case "소셜":
                return Keyword.Category.SOCIAL;
            default:
                return null;
        }
    }
}
