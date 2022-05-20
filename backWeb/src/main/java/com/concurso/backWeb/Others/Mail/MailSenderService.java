package com.concurso.backWeb.Others.Mail;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

@AllArgsConstructor
public class MailSenderService {

    private JavaMailSender mailSender;

    public void sendMail(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("${spring.email.username}");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("EMAIL ENVIADO A " + to + " CORRECTAMENTE");
    }
}
