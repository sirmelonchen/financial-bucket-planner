package de.sirmelonchen.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * The type Email service.
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Instantiates a new Email service.
     *
     * @param mailSender the mail sender
     */
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send.
     *
     * @param to      the to
     * @param content the content
     */
    public void send(String to, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(" no-reply@beugsiud.de ");
            helper.setSubject("Bitte bestätige deine E-Mail");
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Fehler beim E-Mail-Versand", e);
        }
    }
}

