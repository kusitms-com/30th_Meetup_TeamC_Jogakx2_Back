package spring.backend.core.util.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import spring.backend.core.util.email.dto.request.SendEmailRequest;
import spring.backend.core.util.email.exception.MailErrorCode;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Log4j2
public class EmailUtil {
    @Value("${spring.mail.sender}")
    private String sender;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private final JavaMailSender mailSender;

    public void send(SendEmailRequest sendEmailRequest) {
        validateEmailAddress(sendEmailRequest);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(sendEmailRequest.to());
            message.setSubject(sendEmailRequest.subject());
            message.setText(sendEmailRequest.text());
            mailSender.send(message);
        } catch (MailParseException e) {
            log.error("[EmailUtil] Failed to parse email for recipient: {}, subject: {}. Error: {}",
                    sendEmailRequest.to(), sendEmailRequest.subject(), e.getMessage());
            throw MailErrorCode.FAILED_TO_PARSE_MAIL.toException();
        } catch (MailAuthenticationException e) {
            log.error("[EmailUtil] Authentication failed for email sender: {}. Error: {}",
                    sender, e.getMessage());
            throw MailErrorCode.AUTHENTICATION_FAILED.toException();
        } catch (MailSendException e) {
            log.error("[EmailUtil] Error occurred while sending email to recipient: {}, subject: {}. Error: {}",
                    sendEmailRequest.to(), sendEmailRequest.subject(), e.getMessage());
            throw MailErrorCode.ERROR_OCCURRED_SENDING_MAIL.toException();
        } catch (MailException e) {
            log.error("[EmailUtil] General mail error for recipient: {}, subject: {}. Error: {}",
                    sendEmailRequest.to(), sendEmailRequest.subject(), e.getMessage());
            throw MailErrorCode.GENERAL_MAIL_ERROR.toException();
        }
    }

    private void validateEmailAddress(SendEmailRequest request) {
        if (request.to() == null || !EMAIL_PATTERN.matcher(request.to()).matches()) {
            log.error("[EmailUtil] Invalid email address format: {}", request.to());
            throw MailErrorCode.INVALID_MAIL_ADDRESS.toException();
        }
    }
}
