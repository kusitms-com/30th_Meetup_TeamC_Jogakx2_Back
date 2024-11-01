package spring.backend.activity.dto.response;

import spring.backend.activity.domain.value.Keyword;

public record HomeActivityInfoResponse(
        Long id,
        Keyword keyword,
        String title,
        Integer savedTime
) {
}
