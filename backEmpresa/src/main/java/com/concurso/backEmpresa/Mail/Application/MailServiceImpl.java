package com.concurso.backEmpresa.Mail.Application;

import com.concurso.backEmpresa.Mail.Domain.Mail;
import com.concurso.backEmpresa.Mail.Infrastructure.Controller.Dto.MailOutputDto;
import com.concurso.backEmpresa.Mail.Infrastructure.Repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class MailServiceImpl implements MailService{

    @Autowired
    MailRepository mailRepository;

    @Override
    public List<MailOutputDto> getAllMails(){
        return mailRepository.findAll().stream().map(MailOutputDto::new).toList();
    }

    @Override
    public MailOutputDto filterMailById(String id){
        return new MailOutputDto(mailRepository.findById(id).orElseThrow());
    }
    @Override
    public List<Mail> findByDepartureAndArrival(String departure, String arrival){
        List<Mail> mails = mailRepository.findByDepartureAndArrival(departure, arrival);
        return mails.stream().collect(Collectors.toList());
    }


}
