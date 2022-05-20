package com.concurso.backEmpresa.Mail.Infrastructure.Controller.V0;

import com.concurso.backEmpresa.Mail.Application.MailServiceImpl;
import com.concurso.backEmpresa.Mail.Infrastructure.Controller.Dto.MailOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("v0-empresa/mail")
public class ReadMail {

    @Autowired
    MailServiceImpl mailService;

    @GetMapping
    public List<MailOutputDto> findAll(){
        return mailService.getAllMails();
    }

    @GetMapping("{id}")
    public MailOutputDto filterMailById(@PathVariable("id") String id){
        return mailService.filterMailById(id);
    }

    @GetMapping("/details")
    public List<MailOutputDto> filterMailByDepartureAndArrival(@RequestParam("departure") String departure,  @RequestParam("arrival") String arrival){
        return mailService.findByDepartureAndArrival(departure,arrival).stream().map(MailOutputDto::new).toList();
    }
}
