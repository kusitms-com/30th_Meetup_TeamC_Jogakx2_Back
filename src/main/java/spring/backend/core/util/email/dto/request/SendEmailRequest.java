package spring.backend.core.util.email.dto.request;

public record SendEmailRequest(
        String to,
        String subject,
        String text
) {
}
