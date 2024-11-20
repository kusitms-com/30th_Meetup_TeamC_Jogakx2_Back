package spring.backend.recommendation.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.backend.recommendation.infrastructure.link.LinkWebClient;
import spring.backend.recommendation.infrastructure.link.youtube.dto.response.YoutubeResponse;

@Service
@RequiredArgsConstructor
@Log4j2
public class SearchYouTubeService {

    @Value("${youtube.video-url}")
    private String youtubeVideoUrl;

    private final LinkWebClient<YoutubeResponse> youtubeLinkWebClient;

    public String searchYoutube(String query) {
        if (query == null || query.isEmpty()) {
            log.error("[SearchYouTubeService]: query is empty");
            return null;
        }
        YoutubeResponse youtubeResponse = youtubeLinkWebClient.search(query);
        if (youtubeResponse == null || youtubeResponse.items() == null) {
            log.error("[SearchYouTubeService]: youtubeResponse is null");
            return null;
        }
        String videoId = youtubeResponse.items().stream()
                .filter(item -> item.id() != null && item.id().videoId() != null)
                .map(item -> item.id().videoId())
                .findFirst()
                .orElse(null);
        if (videoId == null) {
            log.error("[SearchYouTubeService]: videoId is empty");
            return null;
        }
        return youtubeVideoUrl + videoId;
    }
}
