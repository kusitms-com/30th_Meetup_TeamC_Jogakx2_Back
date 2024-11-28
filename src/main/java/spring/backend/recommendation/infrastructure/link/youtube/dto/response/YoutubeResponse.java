package spring.backend.recommendation.infrastructure.link.youtube.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record YoutubeResponse(
        String kind,
        String etag,
        String nextPageToken,
        String regionCode,
        PageInfo pageInfo,
        List<YoutubeSearchItem> items
) {
    public record PageInfo(
            int totalResults,
            int resultsPerPage
    ) {}

    public record YoutubeSearchItem(
            String kind,
            String etag,
            Id id,
            Snippet snippet
    ) {
        public record Id(
                String kind,
                String videoId,
                String channelId,
                String playlistId
        ) {}

        public record Snippet(
                LocalDateTime publishedAt,
                String channelId,
                String title,
                String description,
                Thumbnails thumbnails,
                String channelTitle,
                String liveBroadcastContent,
                LocalDateTime publishTime
        ) {
            public record Thumbnails(
                    Thumbnail defaultThumbnail,
                    Thumbnail medium,
                    Thumbnail high
            ) {
                public record Thumbnail(
                        String url,
                        int width,
                        int height
                ) {}
            }
        }
    }
}
