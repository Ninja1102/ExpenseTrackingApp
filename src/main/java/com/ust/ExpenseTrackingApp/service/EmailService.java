package com.ust.ExpenseTrackingApp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String to, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Password Reset Request");
        String resetUrl = "http://localhost:8085/api/auth/reset-password?token=" + token;

        String content = "<html><body>"
                + "<p>You requested a password reset.</p>"
                + "<p>Click the link below to reset your password:</p>"
                + "<a href=\"" + resetUrl + "\">Reset Password</a>"
                + "</body></html>";

        helper.setText(content, true);
        mailSender.send(message);
    }
}
