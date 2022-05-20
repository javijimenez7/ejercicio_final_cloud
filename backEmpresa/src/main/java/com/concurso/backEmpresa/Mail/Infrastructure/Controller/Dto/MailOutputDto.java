package com.concurso.backEmpresa.Mail.Infrastructure.Controller.Dto;

import com.concurso.backEmpresa.Mail.Domain.Mail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Data
@NoArgsConstructor
public class MailOutputDto {

    private String idMail;
    private String arrival;
    private String subject;
    private Date fecha;
    private String hora;

    public MailOutputDto(Mail mail){
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");

        setIdMail(mail.getIdMail());
        setArrival(mail.getArrival());
        setSubject(mail.getSubject());
        setFecha(mail.getDate());
        setHora(hourFormat.format(mail.getDate()));
    }
}
