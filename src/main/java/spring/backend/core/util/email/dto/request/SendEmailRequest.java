package spring.backend.core.util.email.dto.request;

import lombok.Builder;

@Builder
public record SendEmailRequest(
        String[] receivers,
        String title,
        String content
) {
}
