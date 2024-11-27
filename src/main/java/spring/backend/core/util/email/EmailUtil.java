package spring.backend.core.util.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
        validateEmailRequest(sendEmailRequest);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(sendEmailRequest.receiver());
            helper.setSubject(sendEmailRequest.title());
            helper.setText(sendEmailRequest.content(), true);

            mailSender.send(message);
        } catch (MailParseException e) {
            log.error("[EmailUtil] Failed to parse email for recipient: {}, subject: {}. Error: {}",
                    sendEmailRequest.receiver(), sendEmailRequest.title(), e.getMessage());
            throw MailErrorCode.FAILED_TO_PARSE_MAIL.toException();
        } catch (MailAuthenticationException e) {
            log.error("[EmailUtil] Authentication failed for email sender: {}. Error: {}",
                    sender, e.getMessage());
            throw MailErrorCode.AUTHENTICATION_FAILED.toException();
        } catch (MailSendException e) {
            log.error("[EmailUtil] Error occurred while sending email to recipient: {}, subject: {}. Error: {}",
                    sendEmailRequest.receiver(), sendEmailRequest.title(), e.getMessage());
            throw MailErrorCode.ERROR_OCCURRED_SENDING_MAIL.toException();
        } catch (MailException | MessagingException e) {
            log.error("[EmailUtil] General mail error for recipient: {}, subject: {}. Error: {}",
                    sendEmailRequest.receiver(), sendEmailRequest.title(), e.getMessage());
            throw MailErrorCode.GENERAL_MAIL_ERROR.toException();
        }
    }

    private void validateEmailRequest(SendEmailRequest request) {
        validateEmailAddress(request);
        validateEmailSubject(request);
        validateEmailText(request);
    }

    private void validateEmailAddress(SendEmailRequest request) {
        if (request.receiver() == null || !EMAIL_PATTERN.matcher(request.receiver()).matches()) {
            log.error("[EmailUtil] Invalid email address format: {}", request.receiver());
            throw MailErrorCode.INVALID_MAIL_ADDRESS.toException();
        }
    }

    private void validateEmailSubject(SendEmailRequest request) {
        if (request.title() == null || request.title().isEmpty()) {
            log.error("[EmailUtil] Invalid email title: {}", request.title());
            throw MailErrorCode.NO_MAIL_TITLE.toException();
        }
    }

    private void validateEmailText(SendEmailRequest request) {
        if (request.content() == null || request.content().isEmpty()) {
            log.error("[EmailUtil] Invalid email subject: {}", request.title());
            throw MailErrorCode.NO_MAIL_CONTENT.toException();
        }
    }
}
