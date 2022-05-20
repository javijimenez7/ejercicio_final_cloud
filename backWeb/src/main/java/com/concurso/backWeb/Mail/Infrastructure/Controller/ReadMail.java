package com.concurso.backWeb.Mail.Infrastructure.Controller;

import com.concurso.backWeb.Mail.Application.MailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v0/mail")

public class ReadMail {

    @Autowired
    MailServiceImpl mailService;


}
