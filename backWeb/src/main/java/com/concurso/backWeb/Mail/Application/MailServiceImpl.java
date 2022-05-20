package com.concurso.backWeb.Mail.Application;

import com.concurso.backWeb.Mail.Domain.Mail;
import com.concurso.backWeb.Mail.Infrastructure.Repository.MailRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service

public class MailServiceImpl implements MailService{

    @Autowired
    MailRepository mailRepository;

    @Override
    public List<Mail> getAllMails(){
        return mailRepository.findAll();
    }

    @Override
    public Mail filterMailById(String id){
        return mailRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Mail> findByDepartureAndArrival(String departure, String arrival){
        List<Mail> mails = mailRepository.findByDepartureAndArrival(departure, arrival);
        return mails.stream().collect(Collectors.toList());
    }

    @Override
    public List<Mail> findByLocalDate(String date){
        List<Mail> mails = mailRepository.findByLocalDate(date);
        return mails.stream().collect(Collectors.toList());
    }
}
