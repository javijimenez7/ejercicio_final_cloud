package com.concurso.backEmpresa.Mail.Application;

import com.concurso.backEmpresa.Mail.Domain.Mail;
import com.concurso.backEmpresa.Mail.Infrastructure.Controller.Dto.MailOutputDto;

import java.util.List;


public interface MailService {
    List<MailOutputDto> getAllMails();

    MailOutputDto filterMailById(String id);

    List<Mail> findByDepartureAndArrival(String departure, String arrival);
}
