package com.concurso.backEmpresa.Others.Mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void send(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setFrom("${spring.email.username}");
        message.setTo(to);
        message.setText(body);

        mailSender.send(message);
        log.info("Mensaje enviado a " + to + " correctamente");
    }

}
