package spring.backend.core.dto.request;

public record SendEmailRequest(
        String to,
        String subject,
        String text
) {
}
