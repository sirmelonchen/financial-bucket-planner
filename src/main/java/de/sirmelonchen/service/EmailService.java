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
    public void send(String to, String subject,String content) {
        MimeMessage message = mailSender.createMimeMessage();
        String from = System.getProperty("MAIL_FROM");

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Fehler beim E-Mail-Versand", e);
        }
    }
}

