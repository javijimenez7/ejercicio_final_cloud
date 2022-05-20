package com.concurso.backWeb.Mail.Application;

import com.concurso.backWeb.Mail.Domain.Mail;

import java.util.List;


public interface MailService {
    List<Mail> getAllMails();

    Mail filterMailById(String id);

    List<Mail> findByDepartureAndArrival(String departure, String arrival);

    List<Mail> findByLocalDate(String date);
}
