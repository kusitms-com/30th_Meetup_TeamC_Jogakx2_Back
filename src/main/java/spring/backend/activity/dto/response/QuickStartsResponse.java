package spring.backend.activity.dto.response;

import java.util.List;

public record QuickStartsResponse(
        List<QuickStartResponse> quickStartResponses
) {
}
