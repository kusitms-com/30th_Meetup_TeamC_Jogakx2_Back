package spring.backend.core.util.email.dto.request;

public record SendEmailRequest(
        String receiver,
        String title,
        String content
) {
}
