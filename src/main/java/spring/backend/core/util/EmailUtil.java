package spring.backend.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import spring.backend.core.dto.request.SendEmailRequest;

@Component
public class EmailUtil {
    @Value("${spring.mail.sender}")
    private String sender;

    private final JavaMailSender mailSender;

    @Autowired
    public EmailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(SendEmailRequest sendEmailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(sendEmailRequest.to());
        message.setSubject(sendEmailRequest.subject());
        message.setText(sendEmailRequest.text());
        mailSender.send(message);
    }
}
